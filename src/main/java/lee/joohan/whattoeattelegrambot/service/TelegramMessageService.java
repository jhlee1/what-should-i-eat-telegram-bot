package lee.joohan.whattoeattelegrambot.service;

import lee.joohan.whattoeattelegrambot.domain.telegram.TelegramMessage;
import lee.joohan.whattoeattelegrambot.repository.TelegramMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/05/31
 */

@RequiredArgsConstructor
@Service
public class TelegramMessageService {
  private final TelegramMessageRepository telegramMessageRepository;

  public Mono<TelegramMessage> create(Message message) {
    return telegramMessageRepository.save(new TelegramMessage(message));
  }
}
