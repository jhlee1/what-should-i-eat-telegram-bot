package lee.joohan.whattoeattelegrambot.service;

import java.util.Arrays;
import java.util.List;
import lee.joohan.whattoeattelegrambot.domain.Ladder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LadderGameServiceTest {
  @Autowired
  LadderGameService ladderGameService;
  @Test
  void play() {
    List<String> players = Arrays.asList("1", "2","3","4","5");
    List<String> results = Arrays.asList("1", "2","3","4","5");

    System.out.println(
        ladderGameService.play(
            ladderGameService.create(players, results)
                .map(Ladder::getId)
                .block())
            .block()
    );
  }
}