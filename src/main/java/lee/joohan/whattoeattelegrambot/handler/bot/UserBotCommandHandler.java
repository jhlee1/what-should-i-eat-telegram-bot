package lee.joohan.whattoeattelegrambot.handler.bot;

import java.util.regex.Pattern;
import lee.joohan.whattoeattelegrambot.common.ResponseMessage;
import lee.joohan.whattoeattelegrambot.exception.AlreadyVerifiedEmailException;
import lee.joohan.whattoeattelegrambot.exception.AlreadyVerifiedTelegramIdException;
import lee.joohan.whattoeattelegrambot.exception.NotFoundUserException;
import lee.joohan.whattoeattelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/05/20
 */

@RequiredArgsConstructor
@Component
@Slf4j
public class UserBotCommandHandler {
  private final UserService userService;

  public Mono<String> verify(Mono<Message> messageMono) {
    return messageMono
        .filter(message -> Pattern.matches("/\\S+ \\S+", message.getText()))
        .switchIfEmpty(Mono.error(IllegalArgumentException::new))
        .flatMap(message ->
            userService.verifyTelegram(
                message.getText().split(" ")[1],
                message.getFrom().getId(),
                message.getFrom().getFirstName(),
                message.getFrom().getLastName()
            )
        )
        .then(Mono.just(ResponseMessage.VERIFY_ACCOUNT))
        .onErrorReturn(IllegalArgumentException.class, ResponseMessage.VERIFY_ACCOUNT_ARGS_ERROR_RESPONSE)
        .onErrorReturn(NotFoundUserException.class, ResponseMessage.VERIFY_ACCOUNT_NOT_FOUND_USER_ERROR_RESPONSE)
        .onErrorReturn(AlreadyVerifiedTelegramIdException.class, ResponseMessage.ALREADY_VERIFIED_TELEGRAM_ID_ERROR_RESPONSE)
        .onErrorReturn(AlreadyVerifiedEmailException.class, ResponseMessage.ALREADY_VERIFIED_EMAIL_ERROR_RESPONSE);
  }
}
