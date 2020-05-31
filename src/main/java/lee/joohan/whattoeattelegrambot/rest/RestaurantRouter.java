package lee.joohan.whattoeattelegrambot.rest;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import lee.joohan.whattoeattelegrambot.handler.rest.RestaurantRestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * Created by Joohan Lee on 2020/05/08
 */

@RequiredArgsConstructor
@Component
public class RestaurantRouter {
  private final RestaurantRestHandler restaurantRestHandler;

  @Bean
  public RouterFunction<?> restaurantRoutes() {
    return RouterFunctions.route()
        .GET("/restaurants", request -> ServerResponse.ok().body(fromValue(restaurantRestHandler.getRestaurants())))
        .POST("/restaurants", request -> ServerResponse.ok().body(fromValue(restaurantRestHandler.createRestaurant(request))))

        .build();
  }
}
