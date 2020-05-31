package lee.joohan.whattoeattelegrambot.handler.bot;

import lee.joohan.whattoeattelegrambot.domain.telegram.TelegramMessage;
import lee.joohan.whattoeattelegrambot.service.TelegramMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/05/31
 */

@RequiredArgsConstructor
@Component
public class TelegramMessageBotCommandHandler {
  private final TelegramMessageService telegramMessageService;
  public Mono<TelegramMessage> create(Message message) {
    return telegramMessageService.create(message);
  }
}
