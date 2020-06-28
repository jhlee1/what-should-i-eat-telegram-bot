package lee.joohan.whattoeattelegrambot.handler.bot;

import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lee.joohan.whattoeattelegrambot.common.ResponseMessage;
import lee.joohan.whattoeattelegrambot.domain.MeGameUser;
import lee.joohan.whattoeattelegrambot.domain.telegram.TelegramMessage;
import lee.joohan.whattoeattelegrambot.service.MeGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/06/18
 */

@RequiredArgsConstructor
@Component
public class MeGameCommandHandler {
  private final MeGameService meGameService;

  public Mono<String> create(Mono<TelegramMessage> messageMono) {
    return messageMono.filter(message -> Pattern.matches("/\\S+", message.getText()))
        .switchIfEmpty(Mono.error(IllegalArgumentException::new))
        .flatMap(message ->
            meGameService.create(
                message.getChat().getId(),
                MeGameUser.builder()
                    .telegramId(message.getFrom().getId())
                    .lastName(message.getFrom().getLastName())
                    .firstName(message.getFrom().getFirstName())
                    .build()
            ))
        .then(Mono.just(ResponseMessage.ME_GAME_CREATED));
  }

  public Mono<String> play(Mono<TelegramMessage> messageMono) {
    return messageMono
        .flatMap(message -> meGameService.addPlayer(
            message.getChat().getId(),
            MeGameUser.builder()
                .telegramId(message.getFrom().getId())
                .lastName(message.getFrom().getLastName())
                .firstName(message.getFrom().getFirstName())
                .build()
        ))
        .map(meGame ->
            meGame.getPlayers()
                .values()
                .stream()
                .map(player ->
                    new StringBuilder(player.getLastName())
                        .append(player.getFirstName())
                        .toString()
                )
                .collect(Collectors.joining("\n"))
        );
  }

  public Mono<String> end(Mono<TelegramMessage> messageMono) {
    return messageMono.flatMap(message -> meGameService.end(message.getChat().getId()))
        .map(meGameUsers ->
            meGameUsers.stream()
                .map(meGameUser ->
                    new StringBuffer(meGameUser.getLastName())
                        .append(meGameUser.getFirstName())
                        .toString())
                .collect(Collectors.joining("\n"))
        );
  }
}
