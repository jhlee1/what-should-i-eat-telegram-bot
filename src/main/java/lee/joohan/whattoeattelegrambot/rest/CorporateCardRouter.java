package lee.joohan.whattoeattelegrambot.rest;

import static lee.joohan.whattoeattelegrambot.common.ErrorCode.NOT_BORROWED_ANY_CARD;

import lee.joohan.whattoeattelegrambot.dto.response.ErrorResponse;
import lee.joohan.whattoeattelegrambot.dto.response.PutBackCorporateCardResponse;
import lee.joohan.whattoeattelegrambot.handler.rest.CorporateCardRestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * Created by Joohan Lee on 2020/05/14
 */

@RequiredArgsConstructor
@Component
public class CorporateCardRouter {
  private final CorporateCardRestHandler corporateCardRestHandler;

  @Bean
  public RouterFunction<?> corporateCardRoutes() {
    return RouterFunctions.route()
        .GET("/corporateCards", corporateCardRestHandler::getCorporateCardStatuses)
        .PUT("/corporateCards/use", corporateCardRestHandler::use)
        .PUT("/corporateCards/putBack", request ->
            ServerResponse.ok()
                .body(corporateCardRestHandler.putBack(request), PutBackCorporateCardResponse.class)
                .doOnError(error -> ServerResponse.badRequest().bodyValue(new ErrorResponse(NOT_BORROWED_ANY_CARD, error.getMessage())))
        )
        .build();
  }

}