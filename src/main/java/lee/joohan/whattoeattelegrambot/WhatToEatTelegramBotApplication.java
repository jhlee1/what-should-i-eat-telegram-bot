package lee.joohan.whattoeattelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.telegram.telegrambots.ApiContextInitializer;

@EnableMongoAuditing
@SpringBootApplication
public class WhatToEatTelegramBotApplication {

  public static void main(String[] args) {
    ApiContextInitializer.init();
    SpringApplication.run(WhatToEatTelegramBotApplication.class, args);
  }

}
