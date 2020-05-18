package lee.joohan.whattoeattelegrambot.handler.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import lee.joohan.whattoeattelegrambot.common.TokenProvider;
import lee.joohan.whattoeattelegrambot.domain.User;
import lee.joohan.whattoeattelegrambot.dto.request.LoginRequest;
import lee.joohan.whattoeattelegrambot.dto.response.LoginResponse;
import lee.joohan.whattoeattelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/05/18
 */

@RequiredArgsConstructor
@Component
public class UserLoginHandler {
  private final UserService userService;
  private final BCryptPasswordEncoder passwordEncoder;
  private final TokenProvider tokenProvider;

  public Mono<ServerResponse> login(ServerRequest request) {
    return
        request.bodyToMono(LoginRequest.class)
            .zipWhen(loginRequest -> userService.findByUsername(Mono.just(loginRequest.getUsername())))
            .flatMap(loginRequestAndUser -> {
              if (passwordEncoder.matches(loginRequestAndUser.getT1().getPassword(), loginRequestAndUser.getT2().getPassword())) {
                return ServerResponse.ok().contentType(APPLICATION_JSON).body(fromValue(new LoginResponse(tokenProvider.generateToken(loginRequestAndUser.getT2()))));
              } else {
                return ServerResponse.badRequest().body(fromValue("Invalid credentials"));
              }
            }).switchIfEmpty(ServerResponse.badRequest().body(fromValue("User does not exist")));
  }

  public Mono<ServerResponse> signUp(ServerRequest request) {
    Mono<User> userMono = request.bodyToMono(User.class);
    return userMono.map(user -> {
          user.setPassword(passwordEncoder.encode(user.getPassword()));

          return user;
        }
    ).flatMap(user -> userService.findByUsername(Mono.just(user.getUsername()))
        .flatMap(dbUser -> ServerResponse.badRequest().body(fromValue("User already exist")))
        .switchIfEmpty(
            userService.getOrRegister(Mono.just(user))
                .flatMap(savedUser ->
                    ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .body(fromValue(new LoginResponse(tokenProvider.generateToken(user))))
                )
        )
    );
  }
}
