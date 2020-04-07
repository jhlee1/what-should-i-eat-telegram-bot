package lee.joohan.whattoeattelegrambot.facade;

import java.util.regex.Pattern;
import lee.joohan.whattoeattelegrambot.common.ResponseMessage;
import lee.joohan.whattoeattelegrambot.domain.User;
import lee.joohan.whattoeattelegrambot.service.RestaurantService;
import lee.joohan.whattoeattelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Created by Joohan Lee on 2020/03/12
 */


@Component
@RequiredArgsConstructor
public class CafeBotCommandFacade {
  private final UserService userService;
  private final RestaurantService restaurantService;

  public String addCafe(Message message) {
    if (!Pattern.matches("/\\S+ \\S+", message.getText())) {
      return ResponseMessage.REGISTER_CAFE_ARGS_ERROR_RESPONSE;
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


}