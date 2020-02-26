package lee.joohan.whattoeattelegrambot.telegram;

import lee.joohan.whattoeattelegrambot.common.BotCommand;
import lee.joohan.whattoeattelegrambot.common.ResponseMessage;
import lee.joohan.whattoeattelegrambot.config.HandleException;
import lee.joohan.whattoeattelegrambot.facade.RestaurantBotCommandFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Created by Joohan Lee on 2020/02/16
 */

@Controller
@RequiredArgsConstructor
public class BotCommandHandler {
  private final RestaurantBotCommandFacade restaurantBotCommandFacade;

  @Transactional
  @HandleException
  public String handle(Message message) {
    String command = message.getText().split(" ")[0];

    switch (command) {
      case BotCommand.ADD_RESTAURANT:
        return restaurantBotCommandFacade.addRestaurant(message);
      case BotCommand.ADD_MENU:
        return restaurantBotCommandFacade.addMenu(message);
      case BotCommand.EDIT_NAME_RESTAURANT:
        return restaurantBotCommandFacade.changeRestaurantName(message);
      case BotCommand.DELETE_RESTAURANT:
          return restaurantBotCommandFacade.deleteRestaurant(message);
      case BotCommand.LIST_RESTAURANT:
        return restaurantBotCommandFacade.listRestaurant();
      case BotCommand.RANDOM_PICK:
        return restaurantBotCommandFacade.randomPickRestaurant(message);
      case BotCommand.LIST_COMMANDS:
          return restaurantBotCommandFacade.listCommands();
      default:
        return ResponseMessage.NO_COMMAND_FOUND_ERROR_RESPONSE;
    }
  }
}