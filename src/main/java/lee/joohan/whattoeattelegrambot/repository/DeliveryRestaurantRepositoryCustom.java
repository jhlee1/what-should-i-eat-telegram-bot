package lee.joohan.whattoeattelegrambot.repository;

import lee.joohan.whattoeattelegrambot.domain.delivery.DeliveryRestaurant;
import reactor.core.publisher.Flux;

public interface DeliveryRestaurantRepositoryCustom {
  Flux<DeliveryRestaurant> randomSample(int num);
}
