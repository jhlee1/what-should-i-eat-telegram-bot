package lee.joohan.whattoeattelegrambot.repository;

import lee.joohan.whattoeattelegrambot.domain.delivery.Delivery;
import lee.joohan.whattoeattelegrambot.domain.delivery.DeliveryStatus;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/07/17
 */
public interface DeliveryRepository extends ReactiveMongoRepository<Delivery, ObjectId> {
  Mono<Delivery> findByRoomIdAndDeliveryStatus(long roomId, DeliveryStatus deliveryStatus);
}
