package lee.joohan.whattoeattelegrambot.repository;

import lee.joohan.whattoeattelegrambot.domain.Restaurant;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/02/15
 */

@Repository
public interface RestaurantRepository extends ReactiveMongoRepository<Restaurant, ObjectId>, RestaurantRepositoryCustom {
  Mono<Restaurant> findByName(String name);
  Flux<Restaurant> findAllByOrderById(Pageable pageable);
}
