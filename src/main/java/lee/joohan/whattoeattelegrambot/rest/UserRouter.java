package lee.joohan.whattoeattelegrambot.rest;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import lee.joohan.whattoeattelegrambot.handler.rest.AuthenticationRestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

/**
 * Created by Joohan Lee on 2020/05/18
 */

@RequiredArgsConstructor
@Configuration
public class UserRouter {
  private final AuthenticationRestHandler authenticationRestHandler;


//  @Bean
//  public RouterFunction userRoute() {
//    return RouterFunctions.route()
//        .GET("/users", accept(MediaType.APPLICATION_JSON), request -> )
//        .GET("/users/{userId}", accept(MediaType.APPLICATION_JSON), request -> )
//        .POST("/users", accept(MediaType.APPLICATION_JSON), request -> )
//        .PUT("/users", accept(MediaType.APPLICATION_JSON), request -> )
//        .DELETE("/users", accept(MediaType.APPLICATION_JSON), request -> )
//        .build();
//  }

  @Bean
  public RouterFunction authRoute() {
    return RouterFunctions.route()
        .POST("/auth/login", accept(MediaType.APPLICATION_JSON), authenticationRestHandler::login)
        .GET("/auth/token", accept(MediaType.APPLICATION_JSON), authenticationRestHandler::token)
        .build();
  }
}
