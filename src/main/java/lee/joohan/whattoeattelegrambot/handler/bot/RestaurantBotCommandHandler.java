package lee.joohan.whattoeattelegrambot.handler.bot;

import static lee.joohan.whattoeattelegrambot.common.BotCommand.ADD_RESTAURANT;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.DELETE_RESTAURANT;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.EAT_OR_NOT;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.LIST_RESTAURANT;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.RANDOM_PICK;
import static lee.joohan.whattoeattelegrambot.common.ResponseMessage.DO_NOT_EAT;
import static lee.joohan.whattoeattelegrambot.common.ResponseMessage.EAT;

import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lee.joohan.whattoeattelegrambot.common.ResponseMessage;
import lee.joohan.whattoeattelegrambot.config.BotCommandRouting;
import lee.joohan.whattoeattelegrambot.domain.Restaurant;
import lee.joohan.whattoeattelegrambot.domain.User;
import lee.joohan.whattoeattelegrambot.domain.telegram.TelegramMessage;
import lee.joohan.whattoeattelegrambot.service.RestaurantService;
import lee.joohan.whattoeattelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/02/16
 */
@Component
@RequiredArgsConstructor
public class RestaurantBotCommandHandler {
  private final RestaurantService restaurantService;
  private final UserService userService;

  @BotCommandRouting(ADD_RESTAURANT)
  @Transactional
  public Mono<String> addRestaurant(TelegramMessage message) {
    if (!Pattern.matches("/\\S+ \\S+", message.getText())) {
      return Mono.just(ResponseMessage.REGISTER_RESTAURANT_ARGS_ERROR_RESPONSE);
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
        .flatMap(objects -> restaurantService.registerFromTelegram(objects.getT1(), objects.getT2()))
        .map(it -> ResponseMessage.REGISTER_RESTAURANT_RESPONSE);
  }

  @BotCommandRouting(RANDOM_PICK)
  public Mono<String> randomPickRestaurant(TelegramMessage message) {
    String[] input = message.getText().split(" ") ;
    int num = 1;

    if (Pattern.matches("/\\S+ \\d+", message.getText())) {
      num = Integer.parseInt(input[1]);
    }

    return restaurantService.randomSample(num)
        .map(Restaurant::getName)
        .collect(Collectors.joining(",\n"));
  }

  @BotCommandRouting(LIST_RESTAURANT)
  public Mono<String> listRestaurant() {
    return restaurantService.getAll()
        .map(Restaurant::getName)
        .sort()
        .collect(Collectors.joining(",\n"));
  }

  @BotCommandRouting(DELETE_RESTAURANT)
  public Mono<String> deleteRestaurant(TelegramMessage message) {
    if (!Pattern.matches("/\\S+ \\S+", message.getText())) {
      return Mono.just(ResponseMessage.DELETE_RESTAURANT_RESPONSE);
    }

    return restaurantService.deleteRestaurant(message.getText().split(" ")[1])
        .map(it -> ResponseMessage.DELETE_RESTAURANT_RESPONSE);
  }

  @BotCommandRouting(EAT_OR_NOT)
  public Mono<String> eatOrNot(TelegramMessage message) {
    if (!Pattern.matches("/\\S+ \\S+", message.getText())) {
      return Mono.just(ResponseMessage.DELETE_RESTAURANT_RESPONSE);
    }

    return Mono.create(monoSink -> {
          if (Math.random() > 0.5) {
            monoSink.success(EAT);
          } else {
            monoSink.success(DO_NOT_EAT);
          }
        }
    );
  }
}
