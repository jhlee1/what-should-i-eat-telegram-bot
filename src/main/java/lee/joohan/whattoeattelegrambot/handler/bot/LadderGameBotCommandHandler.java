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
    return telegramMessageMono.filter(message -> Pattern.matches("/\\S+(\\s*\\S+)+\\s*:\\s*(\\S+\\s*)+", message.getText()))
        .switchIfEmpty(Mono.error(new IllegalArgumentException()))
        .map(telegramMessage -> {
          String[] args = telegramMessage.getText().split(":");
          String[] firstArg = args[0].strip().split(" ");
          String[] players = Arrays.copyOfRange(firstArg, 1, firstArg.length);
          String[] rewards = args[1].strip().split(" ");

          if (players.length != rewards.length) {
            throw new IllegalArgumentException();
          }

          return ladderGameService.play(players, rewards);
        })
        .onErrorReturn(IllegalArgumentException.class, ResponseMessage.START_LADDER_GAME_ARGS_ERROR_RESPONSE);
  }

}
