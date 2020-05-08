package lee.joohan.whattoeattelegrambot.telegram;

import static lee.joohan.whattoeattelegrambot.common.BotCommand.ADD_CAFE;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.ADD_RESTAURANT;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.DELETE_RESTAURANT;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.EAT_OR_NOT;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.EDIT_NAME_RESTAURANT;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.LIST_COMMANDS;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.LIST_CORPORATE_CREDIT_CARD;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.LIST_RESTAURANT;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.NOT_EAT;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.RANDOM_PICK;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.RETURN_CORPORATE_CREDIT_CARD;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.USE_CORPORATE_CREDIT_CARD;
import static lee.joohan.whattoeattelegrambot.common.ResponseMessage.DO_NOT_EAT;

import java.util.Optional;
import lee.joohan.whattoeattelegrambot.common.BotCommand;
import lee.joohan.whattoeattelegrambot.common.ResponseMessage;
import lee.joohan.whattoeattelegrambot.config.HandleException;
import lee.joohan.whattoeattelegrambot.handler.CafeBotCommandHandler;
import lee.joohan.whattoeattelegrambot.handler.CorporateCardBotCommandHandler;
import lee.joohan.whattoeattelegrambot.handler.RestaurantBotCommandHandler;
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
public class BotCommandRouter {
  private final RestaurantBotCommandHandler restaurantBotCommandHandler;
  private final CafeBotCommandHandler cafeBotCommandHandler;
  private final CorporateCardBotCommandHandler corporateCardBotCommandHandler;


  @HandleException
  public Mono<String> handle(Message message) {
    log.info("Received message: {}", message);
    String command = Optional.ofNullable(message.getText())
        .map(it -> it.split(" ")[0])
        .orElse("");

    switch (command) {
      case ADD_RESTAURANT:
        return restaurantBotCommandHandler.addRestaurant(Mono.fromSupplier(() -> message));
      case EDIT_NAME_RESTAURANT:
        return restaurantBotCommandHandler.changeRestaurantName(Mono.fromSupplier(() -> message));
      case DELETE_RESTAURANT:
        return restaurantBotCommandHandler.deleteRestaurant(Mono.fromSupplier(() -> message));
      case LIST_RESTAURANT:
        return restaurantBotCommandHandler.listRestaurant();
      case RANDOM_PICK:
//        if (message.getChat().getId() == -310678804) {
//            return Mono.just("탕수육");
//        }
        return restaurantBotCommandHandler.randomPickRestaurant(Mono.fromSupplier(() -> message));
      case LIST_COMMANDS:
        return restaurantBotCommandHandler.listCommands();
      case NOT_EAT:
        return Mono.just(DO_NOT_EAT);
      case ADD_CAFE:
        return cafeBotCommandHandler.addCafe(Mono.just(message));
      case USE_CORPORATE_CREDIT_CARD:
        return corporateCardBotCommandHandler.useCard(Mono.just(message));
      case RETURN_CORPORATE_CREDIT_CARD:
        return corporateCardBotCommandHandler.putBackCard(Mono.just(message));
      case LIST_CORPORATE_CREDIT_CARD:
        return corporateCardBotCommandHandler.listCards();
      case EAT_OR_NOT:
        return restaurantBotCommandHandler.eatOrNot(Mono.just(message));
      case BotCommand.EMPTY:
        return null;
      default:
        return Mono.just(ResponseMessage.NO_COMMAND_FOUND_ERROR_RESPONSE);
    }
  }
}