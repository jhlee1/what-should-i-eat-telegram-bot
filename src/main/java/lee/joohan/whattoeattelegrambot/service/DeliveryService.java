package lee.joohan.whattoeattelegrambot.service;

import lee.joohan.whattoeattelegrambot.domain.delivery.Delivery;
import lee.joohan.whattoeattelegrambot.domain.delivery.DeliveryRestaurant;
import lee.joohan.whattoeattelegrambot.domain.delivery.DeliveryStatus;
import lee.joohan.whattoeattelegrambot.exception.AlreadyExistDeliveryException;
import lee.joohan.whattoeattelegrambot.exception.NotFoundDeliveryException;
import lee.joohan.whattoeattelegrambot.exception.delivery.AlreadyExistDeliveryRestaurantException;
import lee.joohan.whattoeattelegrambot.repository.DeliveryRepository;
import lee.joohan.whattoeattelegrambot.repository.DeliveryRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/07/17
 */

@RequiredArgsConstructor
@Service
public class DeliveryService {

  private final DeliveryRepository deliveryRepository;
  private final DeliveryRestaurantRepository deliveryRestaurantRepository;

  @Transactional
  public Mono<Delivery> startDelivery(long roomId, String hostName, long telegramId) {
    return deliveryRepository.findByRoomIdAndDeliveryStatus(roomId, DeliveryStatus.STARTED)
        .<Delivery>flatMap(it -> Mono.error(new AlreadyExistDeliveryException(roomId)))
        .switchIfEmpty(deliveryRepository.save(new Delivery(roomId, hostName, telegramId)));
  }

  @Transactional(readOnly = true)
  public Mono<Delivery> get(long roomId, DeliveryStatus deliveryStatus) {
    return deliveryRepository.findByRoomIdAndDeliveryStatus(roomId, deliveryStatus);
  }

  @Transactional
  public Mono<Delivery> addMenu(long roomId, long telegramId, String username, String menuName,
      int menuNum) {
    return deliveryRepository.findByRoomIdAndDeliveryStatus(roomId, DeliveryStatus.STARTED)
        .switchIfEmpty(Mono.error(new NotFoundDeliveryException(roomId)))
        .flatMap(delivery -> {
          delivery.addMenu(telegramId, username, menuName, menuNum);

          return deliveryRepository.save(delivery);
        });
  }

  @Transactional
  public Mono<Delivery> end(long roomId, long telegramId) {
    return deliveryRepository.findByRoomIdAndDeliveryStatus(roomId, DeliveryStatus.STARTED)
        .switchIfEmpty(Mono.error(new NotFoundDeliveryException(roomId)))
        .flatMap(delivery -> {
          delivery.end(telegramId);

          return deliveryRepository.save(delivery);
        });
  }

  @Transactional
  public Mono<DeliveryRestaurant> addRestaurant(ObjectId userId, String name) {
    return deliveryRestaurantRepository.findByName(name)
        .<DeliveryRestaurant>flatMap(
            it -> Mono.error(AlreadyExistDeliveryRestaurantException.fromName(it.getName())))
        .switchIfEmpty(
            deliveryRestaurantRepository.save(
                DeliveryRestaurant.builder()
                    .name(name)
                    .creatorId(userId)
                    .build()
            )
        );
  }

  @Transactional
  public Mono<Void> deleteRestaurant(String name) {
    return deliveryRestaurantRepository.findByName(name)
        .flatMap(it -> deliveryRestaurantRepository.delete(it));
  }

  public Flux<DeliveryRestaurant> listRestaurants() {
    return deliveryRestaurantRepository.findAll();
  }

  public Flux<DeliveryRestaurant> randomRestaurants(int num) {
    return deliveryRestaurantRepository.randomSample(num);
  }

}