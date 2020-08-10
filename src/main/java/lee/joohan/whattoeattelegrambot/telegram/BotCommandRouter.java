package lee.joohan.whattoeattelegrambot.telegram;

import static lee.joohan.whattoeattelegrambot.common.BotCommand.ADD_CAFE;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.ADD_LADDER_GAME_USER_GROUP_MEMBER;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.ADD_RESTAURANT;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.CREATE_LADDER_GAME_USER_GROUP;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.DELETE_LADDER_GAME_USER_GROUP;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.DELETE_RESTAURANT;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.DELIVERY_ADD_MENU;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.DELIVERY_END;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.DELIVERY_START;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.EAT_OR_NOT;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.EDIT_NAME_RESTAURANT;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.LADDER_GAME;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.LIST_CAFE;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.LIST_COMMANDS;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.LIST_CORPORATE_CREDIT_CARD;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.LIST_LADDER_GAME_USER_GROUP;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.LIST_RESTAURANT;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.NOT_EAT;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.PICK_RANDOM_CAFE;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.RANDOM_PICK;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.REMOVE_LADDER_GAME_USER_GROUP_MEMBER;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.RETURN_CORPORATE_CREDIT_CARD;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.SHOW_GAME_GROUP_MEMBER;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.SPLIT_LADDER_GAME_GROUP;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.USE_CORPORATE_CREDIT_CARD;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.VERIFY_ACCOUNT;
import static lee.joohan.whattoeattelegrambot.common.ResponseMessage.DO_NOT_EAT;

import java.util.Optional;
import lee.joohan.whattoeattelegrambot.common.BotCommand;
import lee.joohan.whattoeattelegrambot.common.ResponseMessage;
import lee.joohan.whattoeattelegrambot.handler.bot.CafeBotCommandHandler;
import lee.joohan.whattoeattelegrambot.handler.bot.CorporateCardBotCommandHandler;
import lee.joohan.whattoeattelegrambot.handler.bot.DeliveryBotCommandHandler;
import lee.joohan.whattoeattelegrambot.handler.bot.LadderGameBotCommandHandler;
import lee.joohan.whattoeattelegrambot.handler.bot.RestaurantBotCommandHandler;
import lee.joohan.whattoeattelegrambot.handler.bot.TelegramMessageBotCommandHandler;
import lee.joohan.whattoeattelegrambot.handler.bot.UserBotCommandHandler;
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
  private final UserBotCommandHandler userBotCommandHandler;
  private final TelegramMessageBotCommandHandler telegramMessageBotCommandHandler;
  private final LadderGameBotCommandHandler ladderGameBotCommandHandler;
  private final DeliveryBotCommandHandler deliveryBotCommandHandler;


  public Mono<String> handle(Message telegramMessage) {
        return telegramMessageBotCommandHandler.create(telegramMessage)
        .flatMap(
            message -> {
              String command = Optional.ofNullable(message.getText())
                  .map(it -> it.split(" ")[0])
                  .orElse("");

              //TODO: 작동하는 채팅방 리스트 뽑아서 제한걸기

              switch (command) {
                case ADD_RESTAURANT:
                  return restaurantBotCommandHandler.addRestaurant(message);
                case EDIT_NAME_RESTAURANT:
                  return restaurantBotCommandHandler.changeRestaurantName(message);
                case DELETE_RESTAURANT:
                  return restaurantBotCommandHandler.deleteRestaurant(message);
                case LIST_RESTAURANT:
                  return restaurantBotCommandHandler.listRestaurant();
                case RANDOM_PICK:
                  return restaurantBotCommandHandler.randomPickRestaurant(message);
                case LIST_COMMANDS:
                  return restaurantBotCommandHandler.listCommands();
                case NOT_EAT:
                  return Mono.just(DO_NOT_EAT);
                case ADD_CAFE:
                  return cafeBotCommandHandler.addCafe(message);
                case LIST_CAFE:
                  return cafeBotCommandHandler.list();
                case PICK_RANDOM_CAFE:
                  return cafeBotCommandHandler.random(message);
                case USE_CORPORATE_CREDIT_CARD:
                  return corporateCardBotCommandHandler.useCard(message);
                case RETURN_CORPORATE_CREDIT_CARD:
                  return corporateCardBotCommandHandler.putBackCard(message);
                case LIST_CORPORATE_CREDIT_CARD:
                  return corporateCardBotCommandHandler.listCards();
                case EAT_OR_NOT:
                  return restaurantBotCommandHandler.eatOrNot(message);
                case VERIFY_ACCOUNT:
                  return userBotCommandHandler.verify(message);
                case LADDER_GAME:
                  return ladderGameBotCommandHandler.play(message);
                case DELIVERY_START:
                  return deliveryBotCommandHandler.start(message);
                case DELIVERY_ADD_MENU:
                  return deliveryBotCommandHandler.addMenu(message);
                case DELIVERY_END:
                  return deliveryBotCommandHandler.end(message);
                case CREATE_LADDER_GAME_USER_GROUP:
                  return ladderGameBotCommandHandler.createGameGroup(message);
                case DELETE_LADDER_GAME_USER_GROUP:
                  return ladderGameBotCommandHandler.deleteGameGroup(message);
                case LIST_LADDER_GAME_USER_GROUP:
                  return ladderGameBotCommandHandler.listGameGroups();
                case ADD_LADDER_GAME_USER_GROUP_MEMBER:
                  return ladderGameBotCommandHandler.addGameGroupMember(message);
                case REMOVE_LADDER_GAME_USER_GROUP_MEMBER:
                  return ladderGameBotCommandHandler.removeGameGroupMember(message);
                case SPLIT_LADDER_GAME_GROUP:
                  return ladderGameBotCommandHandler.splitGameGroupMember(message);
                case SHOW_GAME_GROUP_MEMBER:
                  return ladderGameBotCommandHandler.listGameGroupMembers(message);
                case BotCommand.EMPTY:
                  return Mono.empty();
                default:
                  return Mono.just(ResponseMessage.NO_COMMAND_FOUND_ERROR_RESPONSE);
              }
            }
        );
  }
}