package lee.joohan.whattoeattelegrambot.rest;

import lee.joohan.whattoeattelegrambot.handler.rest.RestaurantRestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

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
        .GET("/restaurants", request -> ServerResponse.ok().body(restaurantRestHandler.getRestaurants()))
        .POST("/restaurants", request -> ServerResponse.ok().body(restaurantRestHandler.createRestaurant(request)))

        .build();
  }
}
