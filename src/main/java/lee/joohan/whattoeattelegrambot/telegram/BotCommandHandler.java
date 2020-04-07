package lee.joohan.whattoeattelegrambot.telegram;

import lee.joohan.whattoeattelegrambot.common.BotCommand;
import lee.joohan.whattoeattelegrambot.common.ResponseMessage;
import lee.joohan.whattoeattelegrambot.config.HandleException;
import lee.joohan.whattoeattelegrambot.facade.CafeBotCommandFacade;
import lee.joohan.whattoeattelegrambot.facade.RestaurantBotCommandFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Created by Joohan Lee on 2020/02/16
 */

@Controller
@RequiredArgsConstructor
@Slf4j
public class BotCommandHandler {
  private final RestaurantBotCommandFacade restaurantBotCommandFacade;
  private final CafeBotCommandFacade cafeBotCommandFacade;

  @Transactional
  @HandleException
  public String handle(Message message) {
    log.info("Received message: {}", message);
    String command = message.getText().split(" ")[0];



    switch (command) {
      case BotCommand.ADD_RESTAURANT:
        return restaurantBotCommandFacade.addRestaurant(message);
      case BotCommand.EDIT_NAME_RESTAURANT:
        return restaurantBotCommandFacade.changeRestaurantName(message);
      case BotCommand.DELETE_RESTAURANT:
          return restaurantBotCommandFacade.deleteRestaurant(message);
      case BotCommand.LIST_RESTAURANT:
        return restaurantBotCommandFacade.listRestaurant();
      case BotCommand.RANDOM_PICK:
        if (message.getChat().getId() == -310678804) {
//          return "탕수육";
        }
        return restaurantBotCommandFacade.randomPickRestaurant(message);
      case BotCommand.LIST_COMMANDS:
          return restaurantBotCommandFacade.listCommands();
      case BotCommand.NOT_EAT:
        return "먹지마!";
      case BotCommand.ADD_CAFE:
        return cafeBotCommandFacade.addCafe(message);

      default:
        return ResponseMessage.NO_COMMAND_FOUND_ERROR_RESPONSE;
    }
  }
}