package lee.joohan.whattoeattelegrambot.handler.rest;

import lee.joohan.whattoeattelegrambot.config.security.AccessToken;
import lee.joohan.whattoeattelegrambot.domain.dao.CorporateCardStatus;
import lee.joohan.whattoeattelegrambot.dto.request.PutBackCorporateCardRequest;
import lee.joohan.whattoeattelegrambot.dto.request.UseCorporateCardRequest;
import lee.joohan.whattoeattelegrambot.dto.response.PutBackCorporateCardResponse;
import lee.joohan.whattoeattelegrambot.dto.response.UseCorporateCardResponse;
import lee.joohan.whattoeattelegrambot.service.CorporateCardService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

  public Mono<UseCorporateCardResponse> use(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(UseCorporateCardRequest.class)
        .map(request -> request.getCardNum())
        .zipWith(
            serverRequest
                .exchange()
                .getPrincipal()
                .map(principal -> (AccessToken) principal)
                .map(accessToken -> new ObjectId(accessToken.getCredentials().toString()))
        )
        .flatMap(cardNumAndUserId -> corporateCardService.use(Mono.just(cardNumAndUserId)))
        .map(UseCorporateCardResponse::new);
  }

  public Mono<PutBackCorporateCardResponse> putBack(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(PutBackCorporateCardRequest.class)
        .map(request -> request.getCardNum())
        .zipWith(
            serverRequest
                .exchange()
                .getPrincipal()
                .map(principal -> (AccessToken) principal)
                .map(accessToken -> new ObjectId(accessToken.getCredentials().toString()))
        )
        .flatMap(cardNumAndUserId -> corporateCardService.putBack(Mono.just(cardNumAndUserId)))
        .map(PutBackCorporateCardResponse::new);
  }

}
