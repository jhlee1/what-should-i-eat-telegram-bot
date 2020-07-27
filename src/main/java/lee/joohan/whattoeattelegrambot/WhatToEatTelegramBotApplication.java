package lee.joohan.whattoeattelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.telegram.telegrambots.ApiContextInitializer;
import reactor.tools.agent.ReactorDebugAgent;

@EnableMongoAuditing
@SpringBootApplication
@EnableTransactionManagement
public class WhatToEatTelegramBotApplication {
  static {
    ReactorDebugAgent.init();
    ApiContextInitializer.init();
//    BlockHound.install();
  }

  public static void main(String[] args) {
    SpringApplication.run(WhatToEatTelegramBotApplication.class, args);
  }
}
