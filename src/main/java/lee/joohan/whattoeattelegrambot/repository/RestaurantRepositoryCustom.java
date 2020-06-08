package lee.joohan.whattoeattelegrambot.repository;

import lee.joohan.whattoeattelegrambot.domain.Restaurant;
import reactor.core.publisher.Flux;

/**
 * Created by Joohan Lee on 2020/06/08
 */
public interface RestaurantRepositoryCustom {
  Flux<Restaurant> getRandomSample(int num);
}
