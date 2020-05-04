package lee.joohan.whattoeattelegrambot.facade;

import static lee.joohan.whattoeattelegrambot.common.ResponseMessage.CORPORATE_CARD_ALREADY_IN_USE_ERROR_RESPONSE;
import static lee.joohan.whattoeattelegrambot.common.ResponseMessage.PUT_BACK_CORPORATE_CARD_ARGS_ERROR_RESPONSE;
import static lee.joohan.whattoeattelegrambot.common.ResponseMessage.RETURN_CORPORATE_CARD;
import static lee.joohan.whattoeattelegrambot.common.ResponseMessage.USE_CORPORATE_CARD;
import static lee.joohan.whattoeattelegrambot.common.ResponseMessage.USE_CORPORATE_CARD_ARGS_ERROR_RESPONSE;

import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lee.joohan.whattoeattelegrambot.domain.User;
import lee.joohan.whattoeattelegrambot.exception.corporate_card.CorporateCardAlreadyInUseException;
import lee.joohan.whattoeattelegrambot.service.CorporateCardService;
import lee.joohan.whattoeattelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/04/03
 */

@Component
@RequiredArgsConstructor
public class CorporateCardBotCommandFacade {
  private final CorporateCardService corporateCardService;
  private final UserService userService;

  public Mono<String> useCard(Mono<Message> messageMono) {
    return messageMono
        .filter(message -> Pattern.matches("/\\S+ \\d+", message.getText()))
        .switchIfEmpty(Mono.error(IllegalArgumentException::new))
        .map(message -> Integer.parseInt(message.getText().split(" ")[1]))
        .zipWith(
            messageMono.map(message -> User.builder()
                .telegramId(message.getFrom().getId())
                .firstName(message.getFrom().getFirstName())
                .lastName(message.getFrom().getLastName())
                .build()
            ).flatMap(user -> userService.getOrRegister(Mono.just(user)))
                .map(User::getId)
        )
        .flatMap(item ->corporateCardService.use(Mono.just(item)))
        .then(Mono.just(USE_CORPORATE_CARD))
        .onErrorReturn(IllegalArgumentException.class, USE_CORPORATE_CARD_ARGS_ERROR_RESPONSE)
        .onErrorReturn(CorporateCardAlreadyInUseException.class, CORPORATE_CARD_ALREADY_IN_USE_ERROR_RESPONSE);
  }

  public Mono<String> putBackCard(Mono<Message> messageMono) {
    return messageMono
        .filter(message -> Pattern.matches("/\\S+", message.getText()))
        .switchIfEmpty(Mono.error(IllegalArgumentException::new))
        .map(message -> Integer.parseInt(message.getText().split(" ")[1]))
        .zipWith(
            messageMono.map(message -> User.builder()
                .telegramId(message.getFrom().getId())
                .firstName(message.getFrom().getFirstName())
                .lastName(message.getFrom().getLastName())
                .build()
            ).flatMap(user -> userService.getOrRegister(Mono.just(user)))
                .map(User::getId)
        )
        .doOnNext(tuple -> corporateCardService.putBack(Mono.just(tuple)))
        .then(Mono.just(RETURN_CORPORATE_CARD))
        .onErrorReturn(PUT_BACK_CORPORATE_CARD_ARGS_ERROR_RESPONSE);



//    if (Pattern.matches("/\\S+", message.getText())) {
//      Mono<User> user = userService.get(Mono.just(message.getFrom().getId().longValue()))
//          .map(userMono -> userMono.getId())
//          .flatMap(objectId -> corporateCardService.putBack(objectId))
//          ;
//
//      corporateCardService.putBack(user.getId());
//    } else {
//      if (!Pattern.matches("/\\S+ \\d+", message.getText())) {
//        return ResponseMessage.PUT_BACK_CORPORATE_CARD_ARGS_ERROR_RESPONSE;
//      }
//
//      int cardNum = Integer.parseInt(message.getText().split(" ")[1]);
//
//      User user = userService.getOrRegister(
//          message.getFrom().getId(),
//          message.getFrom().getLastName(),
//          message.getFrom().getFirstName()
//      );
//
//      corporateCardService.putBack(cardNum, user.getId());
//    }
//
//    return ResponseMessage.RETURN_CORPORATE_CARD;
  }

  public Mono<String> listCards() {
    return corporateCardService.listCardStatuses()
        .map(cardStatus -> String.format("카드 번호: %s, 사용자: %s, 반납여부: %s", cardStatus.getCardNum(), cardStatus.getUserInfo().getFullName(), !cardStatus.isBorrowed()))
        .collect(Collectors.joining("\n"));
  }
}
