package lee.joohan.whattoeattelegrambot.telegram;

import static lee.joohan.whattoeattelegrambot.common.BotCommand.ADD_CAFE;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.ADD_RESTAURANT;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.DELETE_RESTAURANT;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.EAT_OR_NOT;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.EDIT_NAME_RESTAURANT;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.LIST_COMMANDS;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.LIST_RESTAURANT;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.NOT_EAT;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.RANDOM_PICK;
import static lee.joohan.whattoeattelegrambot.common.ResponseMessage.DO_NOT_EAT;

import java.util.Optional;
import lee.joohan.whattoeattelegrambot.common.BotCommand;
import lee.joohan.whattoeattelegrambot.common.ResponseMessage;
import lee.joohan.whattoeattelegrambot.config.HandleException;
import lee.joohan.whattoeattelegrambot.facade.CafeBotCommandFacade;
import lee.joohan.whattoeattelegrambot.facade.CorporateCardBotCommandFacade;
import lee.joohan.whattoeattelegrambot.facade.RestaurantBotCommandFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/02/16
 */

@Controller
@RequiredArgsConstructor
@Slf4j
public class BotCommandHandler {
  private final RestaurantBotCommandFacade restaurantBotCommandFacade;
  private final CafeBotCommandFacade cafeBotCommandFacade;
  private final CorporateCardBotCommandFacade corporateCardBotCommandFacade;


  @HandleException
  public Mono<String> handle(Message message) {
    log.info("Received message: {}", message);
    String command = Optional.ofNullable(message.getText())
        .map(it -> it.split(" ")[0])
        .orElse("");

    switch (command) {
      case ADD_RESTAURANT:
        return restaurantBotCommandFacade.addRestaurant(Mono.just(message));
      case EDIT_NAME_RESTAURANT:
        return restaurantBotCommandFacade.changeRestaurantName(Mono.just(message));
      case DELETE_RESTAURANT:
        return restaurantBotCommandFacade.deleteRestaurant(Mono.just(message));
      case LIST_RESTAURANT:
        return restaurantBotCommandFacade.listRestaurant();
      case RANDOM_PICK:
//        if (message.getChat().getId() == -310678804) {
//            return Mono.just("삼식이");
//        }
        return restaurantBotCommandFacade.randomPickRestaurant(Mono.just(message));
      case LIST_COMMANDS:
        return restaurantBotCommandFacade.listCommands();
      case NOT_EAT:
        return Mono.just(DO_NOT_EAT);
      case ADD_CAFE:
        return cafeBotCommandFacade.addCafe(Mono.just(message));
//      case USE_CORPORATE_CREDIT_CARD:
//        return corporateCardBotCommandFacade.useCard(message);
//      case RETURN_CORPORATE_CREDIT_CARD:
//        return corporateCardBotCommandFacade.putBackCard(message);
//      case LIST_CORPORATE_CREDIT_CARD:
//        return corporateCardBotCommandFacade.listCards();
      case EAT_OR_NOT:
        return restaurantBotCommandFacade.eatOrNot(Mono.just(message));
      case BotCommand.EMPTY:
        return null;
      default:
        return Mono.just(ResponseMessage.NO_COMMAND_FOUND_ERROR_RESPONSE);
    }
  }
}