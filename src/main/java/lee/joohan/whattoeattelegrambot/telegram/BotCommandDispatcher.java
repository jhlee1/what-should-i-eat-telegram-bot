package lee.joohan.whattoeattelegrambot.telegram;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lee.joohan.whattoeattelegrambot.config.BotCommand;
import lee.joohan.whattoeattelegrambot.config.BotCommandRoute;
import lee.joohan.whattoeattelegrambot.handler.bot.TelegramMessageBotCommandHandler;
import lombok.RequiredArgsConstructor;
import org.reflections.Reflections;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/08/10
 */

@RequiredArgsConstructor
@Component
public class BotCommandDispatcher {
  private final ApplicationContext applicationContext;
  private final TelegramMessageBotCommandHandler telegramMessageBotCommandHandler;

  public Mono<String> dispatch(Message telegramMessage) {
    return
        applicationContext.getBeansWithAnnotation(BotCommandRoute.class).values().stream().map(it -> it.getClass().meth)

        telegramMessageBotCommandHandler.create(telegramMessage)
        .map()

  }

  private Mono<String> route(String command) {
    Reflections reflections = new Reflections("lee.joohan.whattoeattelegrambot.telegram");
    Set<Method> annotatedMethods = reflections.getMethodsAnnotatedWith(BotCommandRoute.class);

    Map<String, Method> routingMethods = annotatedMethods.stream()
          .collect(Collectors.toMap(method -> method.getAnnotation(BotCommand.class).value(), it -> it));


    routingMethods.get(command).invoke()



  }



}

//  public Mono<String> handle(Message telegramMessage) {
//
//    return telegramMessageBotCommandHandler.create(telegramMessage)
//        .flatMap(
//            message -> {
//              String command = Optional.ofNullable(message.getText())
//                  .map(it -> it.split(" ")[0])
//                  .orElse("");
//
//              //TODO: 작동하는 채팅방 리스트 뽑아서 제한걸기
//
//              switch (command) {
//                case ADD_RESTAURANT:
//                default:
//                  return Mono.just(ResponseMessage.NO_COMMAND_FOUND_ERROR_RESPONSE);
//              }
//            }
//        );
//
//
//}
