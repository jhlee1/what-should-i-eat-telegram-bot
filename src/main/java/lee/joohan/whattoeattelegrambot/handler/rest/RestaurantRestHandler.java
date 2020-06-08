package lee.joohan.whattoeattelegrambot.handler.rest;

import lee.joohan.whattoeattelegrambot.config.security.AccessToken;
import lee.joohan.whattoeattelegrambot.domain.Restaurant;
import lee.joohan.whattoeattelegrambot.dto.request.RegisterRestaurantRequest;
import lee.joohan.whattoeattelegrambot.dto.response.restaurant.RestaurantListResponseDTO;
import lee.joohan.whattoeattelegrambot.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/05/08
 */

@RequiredArgsConstructor
@Component
public class RestaurantRestHandler {
  private final RestaurantService restaurantService;

  @Transactional(readOnly = true)
  public Mono<ServerResponse> getRestaurants(ServerRequest serverRequest) {
    return Mono.zip(
        Mono.just(serverRequest.queryParam("page")
            .map(Integer::parseInt)
            .orElse(0)),
        Mono.just(serverRequest.queryParam("pageSize")
            .map(Integer::parseInt)
            .orElse(40)))
        .map(it -> restaurantService.getAll(it.getT1(), it.getT2())
            .map(RestaurantListResponseDTO::new))
        .flatMap(it -> ServerResponse.ok().body(it, Restaurant.class));
  }

  @Transactional(readOnly = true)
  public Mono<ServerResponse> createRestaurant(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(RegisterRestaurantRequest.class)
        .zipWith(
            serverRequest.exchange()
                .getPrincipal()
                .map(principal -> (AccessToken) principal)
                .map(accessToken -> new ObjectId(accessToken.getCredentials().toString()))
        )
        .map(it -> restaurantService.registerFromRequest(it.getT2(), it.getT1()))
        .flatMap(it -> ServerResponse.ok().bodyValue(it));
  }
}
