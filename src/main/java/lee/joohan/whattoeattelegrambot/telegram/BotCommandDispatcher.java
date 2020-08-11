package lee.joohan.whattoeattelegrambot.telegram;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lee.joohan.whattoeattelegrambot.common.ResponseMessage;
import lee.joohan.whattoeattelegrambot.config.BotCommandRouting;
import lee.joohan.whattoeattelegrambot.handler.bot.TelegramMessageBotCommandHandler;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/08/10
 */

@Slf4j
@Component
public class BotCommandDispatcher {
  private final TelegramMessageBotCommandHandler telegramMessageBotCommandHandler;
  private final ApplicationContext applicationContext;
  private final Map<String, Method> routingMethods;

  public BotCommandDispatcher(
      ApplicationContext applicationContext,
      TelegramMessageBotCommandHandler telegramMessageBotCommandHandler) {
    this.telegramMessageBotCommandHandler = telegramMessageBotCommandHandler;
    this.applicationContext = applicationContext;

    routingMethods = new Reflections("lee.joohan.whattoeattelegrambot.handler.bot", new MethodAnnotationsScanner())
        .getMethodsAnnotatedWith(BotCommandRouting.class)
        .stream()
        .collect(
            Collectors.toMap(
                method -> method.getAnnotation(BotCommandRouting.class).value(),
                Function.identity())
        );
  }

  //TODO: 작동하는 채팅방 리스트 뽑아서 제한걸기
  public Mono<String> dispatch(Message telegramMessage) {
    return telegramMessageBotCommandHandler.create(telegramMessage)
        .flatMap(message -> {
          String command = Optional.ofNullable(message.getText())
              .map(it -> it.split(" ")[0])
              .orElse("");

          final Method routedTo = routingMethods.get(command);

          if (routedTo == null) {
            return Mono.just(ResponseMessage.NO_COMMAND_FOUND_ERROR_RESPONSE);
          }

          final String className = routedTo.getDeclaringClass().getSimpleName();
          final String beanName = Character.toLowerCase(className.charAt(0)) + className.substring(1);

          try {
            if (routedTo.getParameterCount() < 1) {
              return (Mono<String>) routedTo.invoke(applicationContext.getBean(beanName, false));
            }

            return (Mono<String>) routedTo.invoke(applicationContext.getBean(beanName, false), message);
          } catch (Exception e) {
            log.error("Exception: ", e);
            return Mono.just("알수없는 에러 발생");
          }
        });
  }
}
