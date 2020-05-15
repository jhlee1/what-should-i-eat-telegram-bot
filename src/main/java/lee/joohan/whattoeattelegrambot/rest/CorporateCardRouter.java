package lee.joohan.whattoeattelegrambot.rest;

import lee.joohan.whattoeattelegrambot.dto.request.PutBackCorporateCardRequest;
import lee.joohan.whattoeattelegrambot.dto.request.UseCorporateCardRequest;
import lee.joohan.whattoeattelegrambot.dto.response.CorporateCardStatusResponse;
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
                .body(
                    corporateCardRestHandler.use(request.bodyToMono(UseCorporateCardRequest.class))
                    .map(UseCorporateCardResponse::new),
                    UseCorporateCardResponse.class
                )
        )
        .PUT("/corporateCards/putBack", request ->
            ServerResponse.ok()
                .body(
                    corporateCardRestHandler.putBack(request.bodyToMono(PutBackCorporateCardRequest.class))
                        .map(PutBackCorporateCardResponse::new),
                    PutBackCorporateCardResponse.class)
        )
        .build();
  }

}