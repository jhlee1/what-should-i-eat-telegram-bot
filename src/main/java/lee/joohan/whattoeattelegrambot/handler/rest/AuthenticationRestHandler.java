package lee.joohan.whattoeattelegrambot.handler.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import lee.joohan.whattoeattelegrambot.config.security.TokenProvider;
import lee.joohan.whattoeattelegrambot.domain.User;
import lee.joohan.whattoeattelegrambot.dto.request.LoginRequest;
import lee.joohan.whattoeattelegrambot.dto.request.SignUpRequest;
import lee.joohan.whattoeattelegrambot.dto.response.LoginResponse;
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

    public Mono<ServerResponse> login(ServerRequest request) {
        return
                request.bodyToMono(LoginRequest.class)
                        .zipWhen(loginRequest -> userService.findByEmail(Mono.just(loginRequest.getEmail())))
                        .flatMap(loginRequestAndUser -> {
                            if (passwordEncoder.matches(loginRequestAndUser.getT1().getPassword(), loginRequestAndUser.getT2().getPassword())) {
                                return ServerResponse.ok().contentType(APPLICATION_JSON).body(fromValue(new LoginResponse(tokenProvider.generateToken(loginRequestAndUser.getT2()))));
                            } else {
                                return ServerResponse.badRequest().body(fromValue("Invalid credentials"));
                            }
                        }).switchIfEmpty(ServerResponse.badRequest().body(fromValue("User does not exist")));
    }

    public Mono<ServerResponse> signUp(ServerRequest request) {
        Mono<SignUpRequest> userMono = request.bodyToMono(SignUpRequest.class);
        return userMono.flatMap(signUpRequest ->
                userService.findByEmail(Mono.just(signUpRequest.getEmail()))
                        .flatMap(user ->
                                ServerResponse.badRequest()
                                        .body(fromValue(String.format("The email[%s] is already registered.", user.getEmail())))
                        ).switchIfEmpty(
                        userMono.flatMap(it -> {
                            User user = User.builder()
                                    .email(it.getEmail())
                                    .password(it.getPassword())
                                    .build();

                            return userService.register(Mono.just(user));
                        })
                                .flatMap(user ->
                                ServerResponse.ok()
                                        .contentType(APPLICATION_JSON)
                                        .body(new LoginResponse(tokenProvider.generateToken(user)), LoginResponse.class)
                        )
                )
        ).log();
    }
}
