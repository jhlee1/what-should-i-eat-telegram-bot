package lee.joohan.whattoeattelegrambot.handler.bot;

import static lee.joohan.whattoeattelegrambot.common.BotCommand.ADD_CAFE;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.LIST_CAFE;
import static lee.joohan.whattoeattelegrambot.common.BotCommand.PICK_RANDOM_CAFE;

import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lee.joohan.whattoeattelegrambot.common.ResponseMessage;
import lee.joohan.whattoeattelegrambot.config.BotCommandRouting;
import lee.joohan.whattoeattelegrambot.domain.Cafe;
import lee.joohan.whattoeattelegrambot.domain.User;
import lee.joohan.whattoeattelegrambot.domain.telegram.TelegramMessage;
import lee.joohan.whattoeattelegrambot.service.CafeService;
import lee.joohan.whattoeattelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/03/12
 */


@Component
@RequiredArgsConstructor
public class CafeBotCommandHandler {
  private final UserService userService;
  private final CafeService cafeService;

  @BotCommandRouting(ADD_CAFE)
  public Mono<String> addCafe(TelegramMessage message) {
    if (!Pattern.matches("/\\S+ \\S+", message.getText())) {
      return Mono.just(ResponseMessage.REGISTER_CAFE_ARGS_ERROR_RESPONSE);
    }

    return userService.getOrRegister(
        User.builder()
            .firstName(message.getFrom().getFirstName())
            .lastName(message.getFrom().getLastName())
            .telegramId(message.getFrom().getId())
            .build())
        .flatMap(it -> cafeService.register(it, message.getText().split(" ")[1]))
        .map(it -> ResponseMessage.REGISTER_CAFE_RESPONSE);
  }

  @BotCommandRouting(LIST_CAFE)
  public Mono<String> list() {
    return cafeService.getAll()
        .map(Cafe::getName)
        .sort()
        .collect(Collectors.joining(",\n"));
  }

  @BotCommandRouting(PICK_RANDOM_CAFE)
  public Mono<String> random(TelegramMessage telegramMessage) {
    int num = 1;

    if (Pattern.matches("/\\S+ \\d+", telegramMessage.getText())) {
      num = Integer.parseInt(telegramMessage.getText().split(" ")[1]);
    }

    return cafeService.randomSample(num)
        .map(Cafe::getName)
        .sort()
        .collect(Collectors.joining("\n"));
  }
}