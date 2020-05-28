package lee.joohan.whattoeattelegrambot.handler.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import lee.joohan.whattoeattelegrambot.client.GoogleOAuthClient;
import lee.joohan.whattoeattelegrambot.config.security.TokenProvider;
import lee.joohan.whattoeattelegrambot.domain.User;
import lee.joohan.whattoeattelegrambot.dto.request.LoginRequest;
import lee.joohan.whattoeattelegrambot.dto.request.SignUpRequest;
import lee.joohan.whattoeattelegrambot.dto.response.ErrorResponse;
import lee.joohan.whattoeattelegrambot.dto.response.LoginResponse;
import lee.joohan.whattoeattelegrambot.dto.response.SignUpResponse;
import lee.joohan.whattoeattelegrambot.exception.TelegramNotVerifiedException;
import lee.joohan.whattoeattelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
  private final BCryptPasswordEncoder passwordEncoder;
  private final TokenProvider tokenProvider;
  private final GoogleOAuthClient googleOAuthClient;


  public Mono<ServerResponse> login(ServerRequest request) {
    return request.bodyToMono(LoginRequest.class)
        .zipWhen(loginRequest -> userService.findByEmail(Mono.just(loginRequest.getEmail())))
        .flatMap(loginRequestUser -> {
          if (!loginRequestUser.getT2().isTelegramVerified()) {
            return Mono.error(TelegramNotVerifiedException.fromUserId(loginRequestUser.getT2().getId()));
          }
          return Mono.just(loginRequestUser);
        })
        .flatMap(loginRequestAndUser -> {
          if (passwordEncoder.matches(loginRequestAndUser.getT1().getPassword(), loginRequestAndUser.getT2().getPassword())) { //TODO: Encrypt된 걸로 받기 passwordEncoder.match()
            return ServerResponse.ok().contentType(APPLICATION_JSON).body(fromValue(new LoginResponse(tokenProvider.generateToken(loginRequestAndUser.getT2()))));
          } else {
            return ServerResponse.badRequest().body(fromValue(new ErrorResponse("Invalid credentials")));
          }
        }).switchIfEmpty(ServerResponse.badRequest().body(fromValue(new ErrorResponse("User does not exist"))))
        .onErrorResume(TelegramNotVerifiedException.class, error -> ServerResponse.badRequest().bodyValue(new ErrorResponse(error.getMessage())));
  }

  public Mono<ServerResponse> signUp(ServerRequest request) {
    Mono<SignUpRequest> signUpRequestMono = request.bodyToMono(SignUpRequest.class).cache();

    return signUpRequestMono.flatMap(signUpRequest ->
            googleOAuthClient.getUserInfoProfile(signUpRequest.getToken())
                .flatMap(stringObjectMap ->
                        ServerResponse.ok()
                            .body(fromValue(stringObjectMap))


//        userService.findByEmail(Mono.just(signUpRequest.getEmail()))
//            .flatMap(user ->
//                ServerResponse.badRequest()
//                    .body(fromValue(new ErrorResponse("The email[%s] is already registered.", user.getEmail())))
//            ).switchIfEmpty(
//            signUpRequestMono.flatMap(it -> {
//              User user = User.builder()
//                  .email(it.getEmail())
//                  .password(passwordEncoder.encode(it.getPassword()))
//                  .build();
//
//              return userService.register(user);
//            })
//                .flatMap(user ->
//                    ServerResponse.ok()
//                        .contentType(APPLICATION_JSON)
//                        .body(fromValue(new SignUpResponse(true)))
//                )
                )
    );
  }

  public Mono<ServerResponse> token(ServerRequest serverRequest) {
    return serverRequest.principal().flatMap(principal -> ServerResponse.ok().bodyValue(principal)).log();
  }
}
