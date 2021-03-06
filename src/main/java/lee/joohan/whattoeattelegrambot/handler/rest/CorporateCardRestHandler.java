package lee.joohan.whattoeattelegrambot.handler.rest;

import static lee.joohan.whattoeattelegrambot.common.ErrorCode.CORPORATE_CARD_CREATE_FAILURE;
import static lee.joohan.whattoeattelegrambot.common.ErrorCode.CORPORATE_CARD_RETURN_FAILURE;

import lee.joohan.whattoeattelegrambot.config.security.AccessToken;
import lee.joohan.whattoeattelegrambot.dto.request.CreateCorporateCardRequest;
import lee.joohan.whattoeattelegrambot.dto.request.PutBackCorporateCardRequest;
import lee.joohan.whattoeattelegrambot.dto.request.UseCorporateCardRequest;
import lee.joohan.whattoeattelegrambot.dto.response.CorporateCardStatusResponse;
import lee.joohan.whattoeattelegrambot.dto.response.CreateCorporateCardResponse;
import lee.joohan.whattoeattelegrambot.dto.response.ErrorResponse;
import lee.joohan.whattoeattelegrambot.dto.response.PutBackCorporateCardResponse;
import lee.joohan.whattoeattelegrambot.dto.response.UseCorporateCardResponse;
import lee.joohan.whattoeattelegrambot.service.CorporateCardService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/05/14
 */

@RequiredArgsConstructor
@Component
public class CorporateCardRestHandler {
  private final CorporateCardService corporateCardService;

  public Mono<ServerResponse> getCorporateCardStatuses(ServerRequest serverRequest) {
    return corporateCardService.listCardStatuses()
        .map(CorporateCardStatusResponse::new)
        .collectList()
        .flatMap(corporateCardStatusResponse -> ServerResponse.ok().bodyValue(corporateCardStatusResponse));
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public Mono<ServerResponse> create(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(CreateCorporateCardRequest.class)
        .map(CreateCorporateCardRequest::getCardNum)
        .flatMap(corporateCardService::create)
        .map(CreateCorporateCardResponse::new)
        .flatMap(res -> ServerResponse.ok().bodyValue(res))
        .onErrorResume(throwable -> ServerResponse.badRequest().bodyValue(new ErrorResponse(CORPORATE_CARD_CREATE_FAILURE, "카드 생성실패")));
  }

  public Mono<ServerResponse> use(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(UseCorporateCardRequest.class)
        .map(request -> request.getCardNum())
        .zipWith(
            serverRequest
                .exchange()
                .getPrincipal()
                .map(principal -> (AccessToken) principal)
                .map(accessToken -> new ObjectId(accessToken.getCredentials().toString()))
        )
        .flatMap(cardNumAndUserId -> corporateCardService.use(cardNumAndUserId.getT1(), cardNumAndUserId.getT2()))
        .map(UseCorporateCardResponse::new)
        .flatMap(res -> ServerResponse.ok().bodyValue(res))
        .onErrorResume(throwable -> ServerResponse.badRequest().bodyValue(new ErrorResponse(CORPORATE_CARD_RETURN_FAILURE, "카드 반납실패")));
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
        .flatMap(cardNumAndUserId -> corporateCardService.putBack(cardNumAndUserId.getT1(), cardNumAndUserId.getT2()))
        .map(PutBackCorporateCardResponse::new);
  }

}
