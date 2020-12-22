package lee.joohan.whattoeattelegrambot.repository;

import lee.joohan.whattoeattelegrambot.domain.delivery.DeliveryRestaurant;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface DeliveryRestaurantRepository extends ReactiveMongoRepository<DeliveryRestaurant, ObjectId>, DeliveryRestaurantRepositoryCustom {
  Mono<DeliveryRestaurant> findByName(String name);
}

