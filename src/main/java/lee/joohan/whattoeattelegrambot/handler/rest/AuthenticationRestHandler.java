package lee.joohan.whattoeattelegrambot.handler.rest;

import static lee.joohan.whattoeattelegrambot.common.ErrorCode.EMAIL_NOT_VERIFIED;
import static lee.joohan.whattoeattelegrambot.common.ErrorCode.NOT_VALID_EMAIL;
import static lee.joohan.whattoeattelegrambot.common.ErrorCode.TELEGRAM_NOT_VERIFIED;

import lee.joohan.whattoeattelegrambot.client.GoogleOAuthClient;
import lee.joohan.whattoeattelegrambot.config.security.TokenProvider;
import lee.joohan.whattoeattelegrambot.domain.User;
import lee.joohan.whattoeattelegrambot.dto.request.LoginRequest;
import lee.joohan.whattoeattelegrambot.dto.response.ErrorResponse;
import lee.joohan.whattoeattelegrambot.dto.response.LoginResponse;
import lee.joohan.whattoeattelegrambot.exception.EmailNotVerifiedException;
import lee.joohan.whattoeattelegrambot.exception.NotValidEmailException;
import lee.joohan.whattoeattelegrambot.exception.TelegramNotVerifiedException;
import lee.joohan.whattoeattelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/05/18
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthenticationRestHandler {
  private final UserService userService;
  private final TokenProvider tokenProvider;
  private final GoogleOAuthClient googleOAuthClient;

  public Mono<ServerResponse> login(ServerRequest request) {
    return request.bodyToMono(LoginRequest.class)
        .map(LoginRequest::getGoogleAccessToken)
        .flatMap(googleOAuthClient::getUserInfoProfile)
        .flatMap(googleOAuthUserInfoResponse ->
            userService.findByEmail(googleOAuthUserInfoResponse.getEmail())
                .switchIfEmpty(Mono.defer(() -> {
                      if (!googleOAuthUserInfoResponse.getEmail().matches(".*@ogqcorp.com")) {
                        return Mono.error(new NotValidEmailException(googleOAuthUserInfoResponse.getEmail()));
                      }

                      if (!googleOAuthUserInfoResponse.isVerifiedEmail()) {
                        return Mono.error(new EmailNotVerifiedException(googleOAuthUserInfoResponse.getEmail()));
                      }

                      return userService.register(User.builder()
                          .firstName(googleOAuthUserInfoResponse.getGivenName())
                          .lastName(googleOAuthUserInfoResponse.getFamilyName())
                          .email(googleOAuthUserInfoResponse.getEmail())
                          .picture(googleOAuthUserInfoResponse.getPicture())
                          .build());

                    })
                )
        )
        .flatMap(user -> {
          if (!user.isTelegramVerified()) {
            return Mono.error(TelegramNotVerifiedException.fromUserId(user.getId()));
          }
          return Mono.just(user);
        })
        .flatMap(user -> ServerResponse.ok().bodyValue(new LoginResponse(tokenProvider.generateToken(user), user.isAdmin())))
        .onErrorResume(TelegramNotVerifiedException.class, error -> ServerResponse.badRequest().bodyValue(new ErrorResponse(TELEGRAM_NOT_VERIFIED, error.getMessage())))
        .onErrorResume(EmailNotVerifiedException.class, error -> ServerResponse.badRequest().bodyValue(new ErrorResponse(EMAIL_NOT_VERIFIED, error.getMessage())))
        .onErrorResume(NotValidEmailException.class, error -> ServerResponse.badRequest().bodyValue(new ErrorResponse(NOT_VALID_EMAIL, error.getMessage())));
  }

  @PreAuthorize("hasRole('ROLE_USER')")
  public Mono<ServerResponse> token(ServerRequest serverRequest) {
    return serverRequest.principal().flatMap(principal -> ServerResponse.ok().bodyValue(principal)).log();
  }

}
