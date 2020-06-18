package lee.joohan.whattoeattelegrambot.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LadderGameServiceTest {
  @Autowired
  LadderGameService ladderGameService;
  @Test
  void play() {
    String[] players = {"1", "2","3","4","5"};
    String[] results = {"1", "2","3","4","5"};

    System.out.println(ladderGameService.play(players, results));
  }
}