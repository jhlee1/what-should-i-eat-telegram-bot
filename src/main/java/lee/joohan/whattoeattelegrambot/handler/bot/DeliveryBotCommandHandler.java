package lee.joohan.whattoeattelegrambot.handler.bot;

import static java.util.stream.Collectors.summingInt;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.ADD_DELIVERY_RESTAURANT;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.DELETE_DELIVERY_RESTAURANT;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.DELIVERY_ADD_MENU;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.DELIVERY_END;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.DELIVERY_START;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.LIST_DELIVERY_RESTAURANT;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.RANDOM_DELIVERY_PICK;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lee.joohan.whattoeattelegrambot.common.ResponseMessage;
import lee.joohan.whattoeattelegrambot.config.BotCommandRouting;
import lee.joohan.whattoeattelegrambot.domain.User;
import lee.joohan.whattoeattelegrambot.domain.delivery.DeliveryRestaurant;
import lee.joohan.whattoeattelegrambot.domain.telegram.TelegramMessage;
import lee.joohan.whattoeattelegrambot.exception.AlreadyExistDeliveryException;
import lee.joohan.whattoeattelegrambot.exception.NotDeliveryOwnerException;
import lee.joohan.whattoeattelegrambot.exception.NotFoundDeliveryException;
import lee.joohan.whattoeattelegrambot.service.DeliveryService;
import lee.joohan.whattoeattelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/07/19
 */

@RequiredArgsConstructor
@Component
public class DeliveryBotCommandHandler {
  private final DeliveryService deliveryService;
  private final UserService userService;


  @BotCommandRouting(DELIVERY_START)
  public Mono<String> start(TelegramMessage telegramMessage) {
    return deliveryService.startDelivery(
        telegramMessage.getChat().getId(),
        telegramMessage.getFrom().getFullName(),
        telegramMessage.getFrom().getId()
    ).map(delivery -> "생성완료")
        .onErrorReturn(AlreadyExistDeliveryException.class, "이미 배달모집 중입니다. 마무리하고 다시하세요.");
  }

  @BotCommandRouting(DELIVERY_ADD_MENU)
  public Mono<String> addMenu(TelegramMessage telegramMessage) {
    if (!Pattern.matches("/\\S+ \\S+( \\d)?", telegramMessage.getText())) {
      return Mono.just(ResponseMessage.DELIVERY_ADD_MENU_ARGS_ERROR_RESPONSE);
    }
    String[] chunks = telegramMessage.getText().strip()
        .split(" ");

    String menu = chunks[1];
    int num = chunks.length > 2 ? Integer.parseInt(chunks[2]) : 1;

    return deliveryService.addMenu(
        telegramMessage.getChat().getId(),
        telegramMessage.getFrom().getId(),
        telegramMessage.getFrom().getFullName(),
        menu,
        num
    ).map(delivery ->
        delivery.getUsers()
            .values()
            .stream()
            .map(it -> it.getName() + " - " + it.getMenus()
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + " x " + entry.getValue())
                .collect(Collectors.joining(", ")))
            .collect(Collectors.joining("\n")))
        .onErrorReturn(NotFoundDeliveryException.class, ResponseMessage.DELIVERY_NOT_FOUND_ERROR_RESPONSE)
        .onErrorReturn(Exception.class, "error");

  }

  @BotCommandRouting(DELIVERY_END)
  public Mono<String> end(TelegramMessage telegramMessage) {
    return deliveryService.end(
        telegramMessage.getChat().getId(),
        telegramMessage.getFrom().getId()
    ).map(delivery -> delivery.getUsers()
        .values()
        .stream()
        .flatMap(userInfo -> userInfo.getMenus()
            .entrySet()
            .stream()
        )
        .collect(Collectors.groupingBy(Map.Entry::getKey, summingInt(Map.Entry::getValue)))
        .entrySet()
        .stream()
        .map(entry -> entry.getKey() + " x " + entry.getValue())
        .collect(Collectors.joining("\n"))
    )
        .onErrorReturn(NotFoundDeliveryException.class, ResponseMessage.DELIVERY_NOT_FOUND_ERROR_RESPONSE)
        .onErrorReturn(NotDeliveryOwnerException.class, ResponseMessage.ONLY_OWNER_CAN_END_DELIVERY_ERROR_RESPONSE);
  }

  @BotCommandRouting(ADD_DELIVERY_RESTAURANT)
  public Mono<String> addRestaurant(TelegramMessage message) {
    if (!Pattern.matches("/\\S+ \\S+", message.getText())) {
      return Mono.just(ResponseMessage.REGISTER_DELIVERY_RESTAURANT_ARGS_ERROR_RESPONSE);
    }

    return userService.getOrRegister(
        User.builder()
            .firstName(message.getFrom().getFirstName())
            .lastName(message.getFrom().getLastName())
            .telegramId(message.getFrom().getId())
            .build()
    )
        .map(User::getId)
        .zipWith(
            Mono.just(message)
                .map(TelegramMessage::getText)
                .map(s -> s.split(" ")[1])
        )
        .flatMap(objects -> deliveryService.addRestaurant(objects.getT1(), objects.getT2()))
        .map(it -> ResponseMessage.REGISTER_RESTAURANT_RESPONSE);
  }

  @BotCommandRouting(DELETE_DELIVERY_RESTAURANT)
  public Mono<String> deleteRestaurant(TelegramMessage message) {
    if (!Pattern.matches("/\\S+ \\S+", message.getText())) {
      return Mono.just(ResponseMessage.DELETE_DELIVERY_RESTAURANT_ARGS_ERROR_RESPONSE);
    }

    return deliveryService.deleteRestaurant(message.getText().split(" ")[1])
        .map(it -> ResponseMessage.DELETE_RESTAURANT_RESPONSE);
  }

  @BotCommandRouting(RANDOM_DELIVERY_PICK)
  public Mono<String> random(TelegramMessage message) {
    int num = 1;

    if (Pattern.matches("/\\S+ \\d+", message.getText())) {
      num = Integer.parseInt(message.getText().split(" ")[1]);
    }

    return deliveryService.randomRestaurants(num)
        .map(DeliveryRestaurant::getName)
        .collect(Collectors.joining(",\n"));
  }

  @BotCommandRouting(LIST_DELIVERY_RESTAURANT)
  public Mono<String> list(TelegramMessage message) {
    return deliveryService.listRestaurants()
        .map(DeliveryRestaurant::getName)
        .collect(Collectors.joining(",\n"));
  }
}