package lee.joohan.whattoeattelegrambot.handler.rest;

import lee.joohan.whattoeattelegrambot.domain.CorporateCard;
import lee.joohan.whattoeattelegrambot.domain.dao.CorporateCardStatus;
import lee.joohan.whattoeattelegrambot.dto.request.PutBackCorporateCardRequest;
import lee.joohan.whattoeattelegrambot.dto.request.UseCorporateCardRequest;
import lee.joohan.whattoeattelegrambot.service.CorporateCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

/**
 * Created by Joohan Lee on 2020/05/14
 */

@RequiredArgsConstructor
@Component
public class CorporateCardRestHandler {
  private final CorporateCardService corporateCardService;

  public Flux<CorporateCardStatus> getCorporateCardStatuses() {
    return corporateCardService.listCardStatuses();
  }

  public Mono<CorporateCard> use(Mono<UseCorporateCardRequest> useCorporateCardRequestMono) {
    return corporateCardService.use(
        useCorporateCardRequestMono.map(
            useCorporateCardRequest ->
                Tuples.of(
                    useCorporateCardRequest.getCardNum(),
                    useCorporateCardRequest.getUserId())
        )
    );
  }

  public Mono<CorporateCard> putBack(Mono<PutBackCorporateCardRequest> putBackCorporateCardRequestMono) {
    return corporateCardService.putBack(
        putBackCorporateCardRequestMono.map(
            putBackCorporateCardRequest ->
                Tuples.of(
                    putBackCorporateCardRequest.getCardNum(),
                    putBackCorporateCardRequest.getUserId())
        )
    );
  }

}
