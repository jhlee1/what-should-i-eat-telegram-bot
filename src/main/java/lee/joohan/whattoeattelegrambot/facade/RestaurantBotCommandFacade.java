package lee.joohan.whattoeattelegrambot.facade;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lee.joohan.whattoeattelegrambot.common.BotCommand;
import lee.joohan.whattoeattelegrambot.common.ResponseMessage;
import lee.joohan.whattoeattelegrambot.domain.Menu;
import lee.joohan.whattoeattelegrambot.domain.Restaurant;
import lee.joohan.whattoeattelegrambot.domain.User;
import lee.joohan.whattoeattelegrambot.service.RestaurantService;
import lee.joohan.whattoeattelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Created by Joohan Lee on 2020/02/16
 */

@RequiredArgsConstructor
@Component
@Slf4j
public class RestaurantBotCommandFacade {
  private final RestaurantService restaurantService;
  private final UserService userService;

  public String addRestaurant(Message message) {
    if (!Pattern.matches("/\\S+ \\S+", message.getText())) {
      return ResponseMessage.REGISTER_RESTAURANT_ARGS_ERROR_RESPONSE;
    }

    String[] words = message.getText().split(" ");
    String name = words[1];

    User user = userService.getOrRegister(
        message.getFrom().getId(),
        message.getFrom().getLastName(),
        message.getFrom().getFirstName()
    );

    restaurantService.register(name, user);

    return ResponseMessage.REGISTER_RESTAURANT_RESPONSE;

  }

  public String addMenu(Message message) {
    if (!Pattern.matches("/\\S+ \\S+ \\S+ \\d+", message.getText())) {
      return ResponseMessage.REGISTER_MENU_ARGS_ERROR_RESPONSE;
    }

    String[] input = message.getText().split(" ");
    String restaurantName = input[1];
    String menuName = input[2];
    int price = Integer.parseInt(input[3]);

    User user = userService.getOrRegister(
        message.getFrom().getId(),
        message.getFrom().getLastName(),
        message.getFrom().getFirstName()
    );

    Menu menu = Menu.builder()
        .name(menuName)
        .price(price)
        .creator(user)
        .build();

    Restaurant restaurant = restaurantService.addMenu(restaurantName, menu);

    return restaurant.getMenus().stream()
        .map(Menu::getName)
        .collect(Collectors.joining("\n"));
  }

  public String randomPickRestaurant(Message message) {
    String[] input = message.getText().split(" ");
    int num = input.length > 1 ? Integer.parseInt(input[1]) : 1;
    List<Restaurant> restaurants = restaurantService.getAll();

    return new Random().ints(num, 0, restaurants.size())
        .mapToObj(restaurants::get)
        .map(Restaurant::getName)
        .collect(Collectors.joining(",\n"));
  }

  public String listRestaurant() {
    return restaurantService.getAll().stream()
        .map(Restaurant::getName)
        .sorted()
        .collect(Collectors.joining(",\n"));
  }

  public String changeRestaurantName(Message message) {
    if (!Pattern.matches("/\\S+ \\S+ \\S+", message.getText())) {
      return ResponseMessage.CHANGE_RESTAURANT_NAME_ARGS_ERROR_RESPONSE;
    }

    String[] words = message.getText().split(" ");
    String from = words[1];
    String to = words[2];

    User user = userService.getOrRegister(
        message.getFrom().getId(),
        message.getFrom().getLastName(),
        message.getFrom().getFirstName()
    );
    restaurantService.changeName(from, to, user);

    return ResponseMessage.CHANGE_RESTAURANT_NAME_RESPONSE;
  }

  public String deleteRestaurant(Message message) {
    if (!Pattern.matches("/\\S+ \\S+", message.getText())) {
      return ResponseMessage.DELETE_RESTAURANT_ARGS_ERROR_RESPONSE;
    }

    String[] words = message.getText().split(" ");
    String name = words[1];

    restaurantService.deleteRestaurant(name).getName();

    return ResponseMessage.DELETE_RESTAURANT_RESPONSE;
  }

  public String listCommands() {
    return Stream.of(BotCommand.class.getDeclaredFields())
        .map(field -> {
          try {
            return field.get(String.class);
          } catch (IllegalAccessException e) {
            log.error("Failed to get commands.", e);
            return "";
          }
        })
        .map(String::valueOf)
        .collect(Collectors.joining("\n"));
  }
}
