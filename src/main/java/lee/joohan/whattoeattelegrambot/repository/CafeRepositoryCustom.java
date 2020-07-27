package lee.joohan.whattoeattelegrambot.repository;

import lee.joohan.whattoeattelegrambot.domain.Cafe;
import reactor.core.publisher.Flux;

/**
 * Created by Joohan Lee on 2020/07/27
 */
public interface CafeRepositoryCustom {
  Flux<Cafe> getRandomSample(int num);
}
