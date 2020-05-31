package lee.joohan.whattoeattelegrambot.repository;

import lee.joohan.whattoeattelegrambot.domain.dao.CorporateCardStatus;
import reactor.core.publisher.Flux;

/**
 * Created by Joohan Lee on 2020/04/08
 */
public interface CorporateCardRepositoryCustom {
  Flux<CorporateCardStatus> findCardStatuses();

}
