package lee.joohan.whattoeattelegrambot.handler.bot;

import java.util.regex.Pattern;
import lee.joohan.whattoeattelegrambot.common.ResponseMessage;
import lee.joohan.whattoeattelegrambot.domain.User;
import lee.joohan.whattoeattelegrambot.domain.telegram.TelegramMessage;
import lee.joohan.whattoeattelegrambot.service.CafeService;
import lee.joohan.whattoeattelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/03/12
 */


@Component
@RequiredArgsConstructor
public class CafeBotCommandHandler {
  private final UserService userService;
  private final CafeService cafeService;

  @Transactional
  public Mono<String> addCafe(Mono<TelegramMessage> messageMono) {
    return messageMono
        .filter(message -> Pattern.matches("/\\S+ \\S+", message.getText()))
        .switchIfEmpty(Mono.error(new IllegalArgumentException()))
        .flatMap(
            message -> userService.getOrRegister(Mono.just(User.builder()
                .firstName(message.getFrom().getFirstName())
                .lastName(message.getFrom().getLastName())
                .telegramId(message.getFrom().getId())
                .build())))
        .zipWith(
            messageMono
                .map(TelegramMessage::getText)
                .map(s -> s.split(" ")[1])
        )
        .flatMap(objects -> cafeService.register(Mono.just(objects)))
        .then(Mono.just(ResponseMessage.REGISTER_CAFE_RESPONSE))
        .onErrorReturn(ResponseMessage.REGISTER_CAFE_ARGS_ERROR_RESPONSE);
  }
}