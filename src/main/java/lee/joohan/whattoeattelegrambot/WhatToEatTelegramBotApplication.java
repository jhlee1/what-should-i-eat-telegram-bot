package lee.joohan.whattoeattelegrambot;

import lee.joohan.whattoeattelegrambot.config.oauth.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.telegram.telegrambots.ApiContextInitializer;

@EnableConfigurationProperties(AppProperties.class)
@EnableMongoAuditing
@SpringBootApplication
@EnableTransactionManagement
public class WhatToEatTelegramBotApplication {
  static {
    ApiContextInitializer.init();
  }

  public static void main(String[] args) {
    SpringApplication.run(WhatToEatTelegramBotApplication.class, args);
  }

}
