package lee.joohan.whattoeattelegrambot.rest;

import lee.joohan.whattoeattelegrambot.dto.response.CorporateCardStatusResponse;
import lee.joohan.whattoeattelegrambot.dto.response.ErrorResponse;
import lee.joohan.whattoeattelegrambot.dto.response.PutBackCorporateCardResponse;
import lee.joohan.whattoeattelegrambot.dto.response.UseCorporateCardResponse;
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
        .GET("/corporateCards", request ->
            ServerResponse.ok()
                .body(
                    corporateCardRestHandler
                        .getCorporateCardStatuses()
                        .map(CorporateCardStatusResponse::new),
                    CorporateCardStatusResponse.class
                )
        )
        .PUT("/corporateCards/use", request ->
            ServerResponse.ok()
                .body(corporateCardRestHandler.use(request), UseCorporateCardResponse.class)
                .doOnError(error -> ServerResponse.badRequest().bodyValue(new ErrorResponse(error.getMessage())))
        )
        .PUT("/corporateCards/putBack", request ->
            ServerResponse.ok()
                .body(corporateCardRestHandler.putBack(request), PutBackCorporateCardResponse.class)
                .doOnError(error -> ServerResponse.badRequest().bodyValue(new ErrorResponse(error.getMessage())))
        )
        .build();
  }

}