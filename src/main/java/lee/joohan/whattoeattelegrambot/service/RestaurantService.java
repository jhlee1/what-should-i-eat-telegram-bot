package lee.joohan.whattoeattelegrambot.service;

import lee.joohan.whattoeattelegrambot.domain.Restaurant;
import lee.joohan.whattoeattelegrambot.domain.User;
import lee.joohan.whattoeattelegrambot.exception.AlreadyExistRestaurantException;
import lee.joohan.whattoeattelegrambot.exception.NotFoundRestaurantException;
import lee.joohan.whattoeattelegrambot.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
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

  public Mono<Restaurant> register(Mono<Tuple2<User,String>> userRestaurantNameMono) {
    return restaurantRepository.findByName(userRestaurantNameMono.map(Tuple2::getT2))
        .<Restaurant>flatMap(restaurant -> Mono.error(AlreadyExistRestaurantException.fromName(restaurant.getName())))
        .switchIfEmpty(userRestaurantNameMono.flatMap(it ->
                restaurantRepository.save(
                    Restaurant.builder()
                        .name(it.getT2())
                        .creatorId(it.getT1().getId())
                        .build()
                )
            )
        );
  }

  @Transactional(readOnly = true)
  public Flux<Restaurant> getAll() {
    return restaurantRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Mono<Restaurant> get(Mono<String> name) {
    return restaurantRepository.findByName(name);
  }

  @Transactional
  public Mono<Restaurant> changeName(Mono<Tuple2<Tuple2<String,String>,User>> fromToUpdater) {
    return fromToUpdater.flatMap(objects -> restaurantRepository.findByName(Mono.just(objects.getT1().getT1())))
        .switchIfEmpty(Mono.error(NotFoundRestaurantException.noParam()))
        .zipWith(fromToUpdater.map(objects -> objects.getT1().getT2()))
        .doOnNext(restaurant -> restaurant.getT1().changeName(restaurant.getT2()))
        .map(objects -> objects.getT1())
        .flatMap(restaurantRepository::save);
  }

  @Transactional
  public Mono<Void> deleteRestaurant(Mono<String> name) {
    return restaurantRepository.findByName(name)
        .switchIfEmpty(Mono.error(NotFoundRestaurantException.fromName(name.block())))
        .flatMap(restaurantRepository::delete);
  }
}
