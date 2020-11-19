package lee.joohan.whattoeattelegrambot.handler.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/06/18
 */

@RequiredArgsConstructor
@Component
public class MeGameCommandHandler {

  public void create(Mono<Message> messageMono) {

  }

}
