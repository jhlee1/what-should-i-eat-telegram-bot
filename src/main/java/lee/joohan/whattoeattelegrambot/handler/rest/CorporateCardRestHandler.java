package lee.joohan.whattoeattelegrambot.handler.rest;

import lee.joohan.whattoeattelegrambot.domain.dao.CorporateCardStatus;
import lee.joohan.whattoeattelegrambot.service.CorporateCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * Created by Joohan Lee on 2020/05/14
 */

@RequiredArgsConstructor
@Component
public class CorporateCardRestHandler {
  private CorporateCardService corporateCardService;

  public Flux<CorporateCardStatus> getCorporateCardStatuses() {
    return corporateCardService.listCardStatuses();
  }

}
