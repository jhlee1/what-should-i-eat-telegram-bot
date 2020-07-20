package lee.joohan.whattoeattelegrambot.service;

import lee.joohan.whattoeattelegrambot.domain.delivery.Delivery;
import lee.joohan.whattoeattelegrambot.domain.delivery.DeliveryStatus;
import lee.joohan.whattoeattelegrambot.exception.AlreadyExistDeliveryException;
import lee.joohan.whattoeattelegrambot.exception.NotFoundDeliveryException;
import lee.joohan.whattoeattelegrambot.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/07/17
 */

@RequiredArgsConstructor
@Service
public class DeliveryService {
  private final DeliveryRepository deliveryRepository;

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
  public Mono<Delivery> addMenu(long roomId, long telegramId, String username, String menuName, int menuNum) {
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
}
