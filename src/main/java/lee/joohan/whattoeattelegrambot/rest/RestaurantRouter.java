package lee.joohan.whattoeattelegrambot.rest;

import lee.joohan.whattoeattelegrambot.handler.rest.RestaurantRestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

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
        .GET("/restaurants", restaurantRestHandler::getRestaurants)
        .POST("/restaurants", restaurantRestHandler::createRestaurant)
        .build();
  }
}
