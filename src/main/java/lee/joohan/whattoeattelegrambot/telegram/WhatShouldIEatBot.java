package lee.joohan.whattoeattelegrambot.telegram;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/02/15
 * Useful for Debugging https://api.telegram.org/bot{token}/getUpdates
 */


@Slf4j
@RequiredArgsConstructor
@Component
public class WhatShouldIEatBot extends TelegramLongPollingBot {

  @Value("${telegram.bot.name}")
  private String name;
  @Value("${telegram.bot.key}")
  private String key;

  private final BotCommandRouter botCommandRouter;

  @Override
  public void onUpdateReceived(Update update) {

    botCommandRouter.handle(Mono.fromSupplier(() -> update.getMessage()))
        .doOnNext(s -> reply(update.getMessage().getChatId(), s))
//        .log()
        .subscribe();
  }

  @Override
  public String getBotUsername() {
    return name;
  }

  @Override
  public String getBotToken() {
    return key;
  }

  private boolean reply(long chatId, String message) {
    SendMessage sendMessageRequest = new SendMessage();

    sendMessageRequest.setChatId(chatId);
    sendMessageRequest.setText(message);

    try {
      execute(sendMessageRequest);
      return true;
    } catch (TelegramApiException e) {
      log.error("Failed to reply to the chat group [chatId: {} , message: {}]", chatId, message, e);
    }

    return false;
  }
}