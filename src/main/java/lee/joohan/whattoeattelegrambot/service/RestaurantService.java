package lee.joohan.whattoeattelegrambot.service;

import lee.joohan.whattoeattelegrambot.domain.Restaurant;
import lee.joohan.whattoeattelegrambot.domain.User;
import lee.joohan.whattoeattelegrambot.dto.request.RegisterRestaurantRequest;
import lee.joohan.whattoeattelegrambot.exception.AlreadyExistRestaurantException;
import lee.joohan.whattoeattelegrambot.exception.NotFoundRestaurantException;
import lee.joohan.whattoeattelegrambot.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

/**
 * Created by Joohan Lee on 2020/02/15
 */

@RequiredArgsConstructor
@Service
public class RestaurantService {
  private final RestaurantRepository restaurantRepository;

  public Mono<Restaurant> registerFromTelegram(ObjectId userId, String restaurantName) {
    return restaurantRepository.findByName(restaurantName)
        .<Restaurant>flatMap(restaurant -> Mono.error(AlreadyExistRestaurantException.fromName(restaurant.getName())))
        .switchIfEmpty(
            restaurantRepository.save(
                Restaurant.builder()
                    .name(restaurantName)
                    .creatorId(userId)
                    .build()
            )
        );
  }

  public Mono<Restaurant> registerFromRequest(ObjectId userId ,RegisterRestaurantRequest restaurantRequest) {
    return restaurantRepository.findByName(restaurantRequest.getName())
        .switchIfEmpty(restaurantRepository.save(Restaurant.builder()
            .name(restaurantRequest.getName())
            .creatorId(userId)
            .address(restaurantRequest.getAddress())
            .build()))
        .flatMap(restaurant -> Mono.error(AlreadyExistRestaurantException.fromName(restaurant.getName())));
  }

  @Transactional(readOnly = true)
  public Flux<Restaurant> getAll() {
    return restaurantRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Flux<Restaurant> getAll(int page, int pageSize) {
    return restaurantRepository.findAllByOrderById(PageRequest.of(page, pageSize, Sort.by(Direction.DESC, "id")));
  }

  @Transactional(readOnly = true)
  public Mono<Restaurant> get(String name) {
    return restaurantRepository.findByName(name);
  }

  @Transactional
  public Mono<Restaurant> changeName(Mono<Tuple2<Tuple2<String,String>,User>> fromToUpdater) {
    return fromToUpdater.flatMap(objects -> restaurantRepository.findByName(objects.getT1().getT1()))
        .switchIfEmpty(Mono.error(NotFoundRestaurantException.noParam()))
        .zipWith(fromToUpdater.map(objects -> objects.getT1().getT2()))
        .doOnNext(restaurant -> restaurant.getT1().changeName(restaurant.getT2()))
        .map(objects -> objects.getT1())
        .flatMap(restaurantRepository::save);
  }

  @Transactional
  public Mono<Void> deleteRestaurant(String name) {
    return restaurantRepository.findByName(name)
        .switchIfEmpty(Mono.error(NotFoundRestaurantException.fromName(name)))
        .flatMap(restaurantRepository::delete);
  }
}
