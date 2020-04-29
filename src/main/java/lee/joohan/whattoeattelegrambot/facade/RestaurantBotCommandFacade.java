package lee.joohan.whattoeattelegrambot.facade;

import static lee.joohan.whattoeattelegrambot.common.ResponseMessage.DO_NOT_EAT;
import static lee.joohan.whattoeattelegrambot.common.ResponseMessage.EAT;

import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lee.joohan.whattoeattelegrambot.common.BotCommand;
import lee.joohan.whattoeattelegrambot.common.ResponseMessage;
import lee.joohan.whattoeattelegrambot.domain.Restaurant;
import lee.joohan.whattoeattelegrambot.domain.User;
import lee.joohan.whattoeattelegrambot.service.RestaurantService;
import lee.joohan.whattoeattelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

/**
 * Created by Joohan Lee on 2020/02/16
 */

@RequiredArgsConstructor
@Component
@Slf4j
public class RestaurantBotCommandFacade {
  private final RestaurantService restaurantService;
  private final UserService userService;
  private final static Random random = new Random();

  @Transactional
  public Mono<String> addRestaurant(Mono<Message> messageMono) {
    return messageMono.filter(message -> !Pattern.matches("/\\S+ \\S+", message.getText()))
        .<Message>flatMap(message -> Mono.error(new IllegalArgumentException()))
        .switchIfEmpty(messageMono)
        .flatMap(message -> userService.getOrRegister(Mono.just(User.builder()
                .firstName(message.getFrom().getFirstName())
                .lastName(message.getFrom().getLastName())
                .telegramId(message.getFrom().getId())
                .build()
            )
            )
        )
        .zipWith(messageMono.map(Message::getText).map(s -> s.split(" ")[1]))
        .flatMap(objects -> restaurantService.register(Mono.just(objects)))
        .then(Mono.just(ResponseMessage.REGISTER_RESTAURANT_RESPONSE))
        .onErrorReturn(ResponseMessage.REGISTER_RESTAURANT_ARGS_ERROR_RESPONSE);
  }

//  public String addMenu(Message message) {
//    if (!Pattern.matches("/\\S+ \\S+ \\S+ \\d+", message.getText())) {
//      return ResponseMessage.REGISTER_MENU_ARGS_ERROR_RESPONSE;
//    }
//
//    String[] input = message.getText().split(" ");
//    String restaurantName = input[1];
//    String menuName = input[2];
//    int price = Integer.parseInt(input[3]);
//
//    User user = userService.getOrRegister(
//        message.getFrom().getId(),
//        message.getFrom().getLastName(),
//        message.getFrom().getFirstName()
//    );
//
//    Menu menu = Menu.builder()
//        .name(menuName)
//        .price(price)
//        .creator(user)
//        .build();
//
//    Restaurant restaurant = restaurantService.addMenu(restaurantName, menu);
//
//    return restaurant.getMenus().stream()
//        .map(Menu::getName)
//        .collect(Collectors.joining("\n"));
//  }

  public Mono<String> randomPickRestaurant(Mono<Message> messageMono) {
    return messageMono.map(message -> message.getText().split(" "))
        .map(input -> input.length > 1 ? Integer.parseInt(input[1]) : 1)
        .flatMap(num ->
            restaurantService.getAll()
                .collectList()
                .map(restaurants ->
                    random.ints(num, 0, restaurants.size())
                        .mapToObj(restaurants::get)
                        .map(Restaurant::getName)
                        .collect(Collectors.joining(",\n"))
                )
        );
  }

  public Mono<String> listRestaurant() {
    return Mono.just(restaurantService.getAll()
        .map(Restaurant::getName)
        .sort()
        .toStream()
        .collect(Collectors.joining(",\n")));
  }

  public Mono<String> changeRestaurantName(Mono<Message> messageMono) {
    return messageMono.filter(message -> Pattern.matches("/\\S+ \\S+ \\S+", message.getText()))
        .switchIfEmpty(Mono.error(new IllegalArgumentException()))
        .map(message -> {
          final String[] strings = message.getText().split(" ");
          return Tuples.of(strings[1], strings[2]);
        })
        .zipWith(messageMono
            .map(message -> User.builder()
                .telegramId(message.getFrom().getId())
                .firstName(message.getFrom().getFirstName())
                .lastName(message.getFrom().getLastName())
                .build())
            .flatMap(message -> userService.getOrRegister(Mono.just(message)))
        )
        .flatMap(fromToUser -> restaurantService.changeName(Mono.just(fromToUser)))
        .then(Mono.just(ResponseMessage.CHANGE_RESTAURANT_NAME_RESPONSE))
        .onErrorReturn(ResponseMessage.CHANGE_RESTAURANT_NAME_ARGS_ERROR_RESPONSE);
  }

  public Mono<String> deleteRestaurant(Mono<Message> messageMono) {
    return messageMono.filter(message -> Pattern.matches("/\\S+ \\S+", message.getText()))
        .switchIfEmpty(Mono.error(new IllegalArgumentException()))
        .map(message -> Mono.just(message.getText().split(" ")[1]))
        .flatMap(restaurantService::deleteRestaurant)
        .then(Mono.just(ResponseMessage.DELETE_RESTAURANT_RESPONSE))
        .onErrorReturn(IllegalArgumentException.class, ResponseMessage.DELETE_RESTAURANT_RESPONSE);
  }

  public Mono<String> listCommands() {
    return Mono.just(
        Flux.fromArray(BotCommand.class.getDeclaredFields())
            .map(field -> {
              try {
                return field.get(String.class);
              } catch (IllegalAccessException e) {
                log.error("Failed to get commands.", e);
                return "";
              }
            })
            .map(String::valueOf)
            .toStream()
            .collect(Collectors.joining("\n"))
    );
  }

  public Mono<String> eatOrNot(Mono<Message> messageMono) {
    return messageMono.filter(message -> Pattern.matches("/\\S+ \\S+", message.getText()))
        .<String>then(
            Mono.create(monoSink -> {
                  if (Math.random() > 0.5) {
                    monoSink.success(EAT);
                  } else {
                    monoSink.success(DO_NOT_EAT);
                  }
                }
            )
        )
        .switchIfEmpty(Mono.just(ResponseMessage.EAT_OR_NOT_ARGS_ERROR_RESPONSE));
  }
}
