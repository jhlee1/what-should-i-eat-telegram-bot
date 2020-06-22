package lee.joohan.whattoeattelegrambot.handler.bot;


import java.util.Arrays;
import java.util.regex.Pattern;
import lee.joohan.whattoeattelegrambot.common.ResponseMessage;
import lee.joohan.whattoeattelegrambot.domain.telegram.TelegramMessage;
import lee.joohan.whattoeattelegrambot.service.LadderGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class LadderGameBotCommandHandler {
  private final LadderGameService ladderGameService;

  public Mono<String> play(Mono<TelegramMessage> telegramMessageMono) {
    return telegramMessageMono.filter(message -> Pattern.matches("/\\S+ (\\S+)+ | (\\S+)+", message.getText()))
        .switchIfEmpty(Mono.error(new IllegalArgumentException()))
        .map(telegramMessage -> {
          String[] args = telegramMessage.getText().split("\\|");
          String[] firstArg = args[0].split(" ");
          String[] players = Arrays.copyOfRange(firstArg, 1, firstArg.length - 1);
          String[] rewards = args[1].split(" ");

          if (players.length != rewards.length) {
            throw new IllegalArgumentException();
          }

          return ladderGameService.play(players, rewards);
        })
        .onErrorReturn(IllegalArgumentException.class, ResponseMessage.ALREADY_VERIFIED_EMAIL_ERROR_RESPONSE);
  }

}
