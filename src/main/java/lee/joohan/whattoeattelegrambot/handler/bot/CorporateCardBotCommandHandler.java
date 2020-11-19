package lee.joohan.whattoeattelegrambot.handler.bot;

import static lee.joohan.whattoeattelegrambot.common.BotCommand.LIST_CORPORATE_CREDIT_CARD;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.PUT_BACK_CORPORATE_CREDIT_CARD;
import static lee.joohan.whattoeattelegrambot.common.ResponseMessage.CORPORATE_CARD_ALREADY_IN_RETURNED_ERROR_RESPONSE;
import static lee.joohan.whattoeattelegrambot.common.ResponseMessage.CORPORATE_CARD_ALREADY_IN_USE_ERROR_RESPONSE;
import static lee.joohan.whattoeattelegrambot.common.ResponseMessage.PUT_BACK_CORPORATE_CARD_ARGS_ERROR_RESPONSE;
import static lee.joohan.whattoeattelegrambot.common.ResponseMessage.PUT_BACK_NOT_OWNED_CORPORATE_CARD_ERROR_RESPONSE;
import static lee.joohan.whattoeattelegrambot.common.ResponseMessage.RETURN_CORPORATE_CARD;
import static lee.joohan.whattoeattelegrambot.common.ResponseMessage.USE_CORPORATE_CARD;
import static lee.joohan.whattoeattelegrambot.common.ResponseMessage.USE_CORPORATE_CARD_ARGS_ERROR_RESPONSE;

import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lee.joohan.whattoeattelegrambot.config.BotCommandRouting;
import lee.joohan.whattoeattelegrambot.domain.User;
import lee.joohan.whattoeattelegrambot.domain.telegram.TelegramMessage;
import lee.joohan.whattoeattelegrambot.exception.corporate_card.CorporateCardAlreadyInUseException;
import lee.joohan.whattoeattelegrambot.exception.corporate_card.CorporateCardAlreadyReturnedException;
import lee.joohan.whattoeattelegrambot.exception.corporate_card.NotBorrowedAnyCardException;
import lee.joohan.whattoeattelegrambot.exception.corporate_card.NotFoundCorporateCardException;
import lee.joohan.whattoeattelegrambot.service.CorporateCardService;
import lee.joohan.whattoeattelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/04/03
 */

@Component
@RequiredArgsConstructor
public class CorporateCardBotCommandHandler {
  private final CorporateCardService corporateCardService;
  private final UserService userService;

  @BotCommandRouting(USE_CORPORATE_CARD)
  public Mono<String> useCard(TelegramMessage message) {
    if (!Pattern.matches("/\\S+ \\d+", message.getText())) {
      return Mono.just(USE_CORPORATE_CARD_ARGS_ERROR_RESPONSE);
    }

    int cardNum = Integer.parseInt(message.getText().split(" ")[1]);

    return userService.getOrRegister(User.builder()
        .telegramId(message.getFrom().getId())
        .firstName(message.getFrom().getFirstName())
        .lastName(message.getFrom().getLastName())
        .build()
    )
        .flatMap(item ->corporateCardService.use(cardNum, item.getId()))
        .map(it -> USE_CORPORATE_CARD)
        .onErrorReturn(NotFoundCorporateCardException.class, CORPORATE_CARD_ALREADY_IN_USE_ERROR_RESPONSE)
        .onErrorReturn(CorporateCardAlreadyInUseException.class, CORPORATE_CARD_ALREADY_IN_USE_ERROR_RESPONSE);
  }

  @BotCommandRouting(PUT_BACK_CORPORATE_CREDIT_CARD)
  public Mono<String> putBackCard(TelegramMessage message) {
    if (Pattern.matches("/\\S+ \\d+", message.getText())) {
      int cardNum = Integer.parseInt(message.getText().split(" ")[1]);

      return userService.getOrRegister(
          User.builder()
              .telegramId(message.getFrom().getId())
              .firstName(message.getFrom().getFirstName())
              .lastName(message.getFrom().getLastName())
              .build()
      )
          .flatMap(it -> corporateCardService.putBack(cardNum, it.getId()))
          .map(it -> RETURN_CORPORATE_CARD)
          .onErrorReturn(NotBorrowedAnyCardException.class, PUT_BACK_NOT_OWNED_CORPORATE_CARD_ERROR_RESPONSE)
          .onErrorReturn(CorporateCardAlreadyReturnedException.class, CORPORATE_CARD_ALREADY_IN_RETURNED_ERROR_RESPONSE);
    }

    if (!Pattern.matches("/\\S+", message.getText())) {
      return Mono.just(PUT_BACK_CORPORATE_CARD_ARGS_ERROR_RESPONSE);
    }

    return userService.getOrRegister(
        User.builder()
            .telegramId(message.getFrom().getId())
            .firstName(message.getFrom().getFirstName())
            .lastName(message.getFrom().getLastName())
            .build())
        .flatMapMany(user -> corporateCardService.putBackOwnCard(user.getId()))
        .last()
        .map(it -> RETURN_CORPORATE_CARD)
        .onErrorReturn(NotBorrowedAnyCardException.class, PUT_BACK_NOT_OWNED_CORPORATE_CARD_ERROR_RESPONSE)
        .onErrorReturn(CorporateCardAlreadyReturnedException.class, CORPORATE_CARD_ALREADY_IN_RETURNED_ERROR_RESPONSE);
  }

  @BotCommandRouting(LIST_CORPORATE_CREDIT_CARD)
  public Mono<String> listCards() {
    return corporateCardService.listCardStatuses()
        .map(cardStatus ->
            String.format(
                "카드 번호: %s, 사용자: %s, 반납여부: %s",
                cardStatus.getCardNum(),
                cardStatus.getUserInfo().getFullName(),
                !cardStatus.isBorrowed())
        )
        .collect(Collectors.joining("\n"))
        .switchIfEmpty(Mono.just("등록된 카드가 없습니다."));
  }
}
