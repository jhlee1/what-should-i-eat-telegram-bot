package lee.joohan.whattoeattelegrambot.handler.rest;

import lee.joohan.whattoeattelegrambot.domain.Restaurant;
import lee.joohan.whattoeattelegrambot.dto.request.RegisterRestaurantRequest;
import lee.joohan.whattoeattelegrambot.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/05/08
 */

@RequiredArgsConstructor
@Component
public class RestaurantRestHandler {
  private final RestaurantService restaurantService;

  @Transactional(readOnly = true)
  public Flux<Restaurant> getRestaurants() {
    return restaurantService.getAll();
  }

  @Transactional(readOnly = true)
  public Mono<Restaurant> createRestaurant(ServerRequest serverRequest) {
    return restaurantService.registerFromRequest(
        Mono.fromSupplier(() ->
            serverRequest.headers()
                .header("Token") //TODO: JWT Token 이나 Security 쓸 방법 찾기
                .get(0))
            .map(ObjectId::new)
            .zipWith(serverRequest.bodyToMono(RegisterRestaurantRequest.class))
    );
  }


}
