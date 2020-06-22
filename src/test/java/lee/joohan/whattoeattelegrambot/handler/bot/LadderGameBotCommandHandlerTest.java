package lee.joohan.whattoeattelegrambot.handler.bot;

import static org.junit.jupiter.api.Assertions.*;

import lee.joohan.whattoeattelegrambot.domain.telegram.TelegramMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Message;
import reactor.core.publisher.Mono;

@SpringBootTest
class LadderGameBotCommandHandlerTest {
  @Autowired
  LadderGameBotCommandHandler ladderGameBotCommandHandler;

  @Test
  void play() {
    TelegramMessage telegramMessage = new TelegramMessage();

    telegramMessage.setText("/사다리게임 테스터1 테스터2 : 테스트2 테스터1");


    String result = ladderGameBotCommandHandler.play(Mono.just(telegramMessage))
        .log()
        .block();
  }
}