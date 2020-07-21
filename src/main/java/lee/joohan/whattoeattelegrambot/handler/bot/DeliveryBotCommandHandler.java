package lee.joohan.whattoeattelegrambot.handler.bot;

import static java.util.stream.Collectors.summingInt;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lee.joohan.whattoeattelegrambot.common.ResponseMessage;
import lee.joohan.whattoeattelegrambot.domain.telegram.TelegramMessage;
import lee.joohan.whattoeattelegrambot.exception.AlreadyExistDeliveryException;
import lee.joohan.whattoeattelegrambot.exception.NotDeliveryOwnerException;
import lee.joohan.whattoeattelegrambot.exception.NotFoundDeliveryException;
import lee.joohan.whattoeattelegrambot.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/07/19
 */

@RequiredArgsConstructor
@Component
public class DeliveryBotCommandHandler {
  private final DeliveryService deliveryService;


  public Mono<String> start(TelegramMessage telegramMessage) {
    return deliveryService.startDelivery(
        telegramMessage.getChat().getId(),
        telegramMessage.getFrom().getFullName(),
        telegramMessage.getFrom().getId()
    ).map(delivery -> "생성완료")
        .onErrorReturn(AlreadyExistDeliveryException.class, "이미 배달모집 중입니다. 마무리하고 다시하세요.");
  }

  public Mono<String> addMenu(TelegramMessage telegramMessage) {
    if (!Pattern.matches("/\\S+ \\S+( \\d)?", telegramMessage.getText())) {
      return Mono.just(ResponseMessage.DELIVERY_ADD_MENU_ARGS_ERROR_RESPONSE);
    }
    String[] chunks = telegramMessage.getText().strip()
        .split(" ");

    String menu = chunks[1];
    int num = chunks.length > 2 ? Integer.parseInt(chunks[2]) : 1;

    return deliveryService.addMenu(
        telegramMessage.getChat().getId(),
        telegramMessage.getFrom().getId(),
        telegramMessage.getFrom().getFullName(),
        menu,
        num
    ).map(delivery ->
        delivery.getUsers()
            .values()
            .stream()
            .map(it -> it.getName() + " - " + it.getMenus()
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + " x " + entry.getValue())
                .collect(Collectors.joining(", ")))
            .collect(Collectors.joining("\n")))
        .onErrorReturn(NotFoundDeliveryException.class, ResponseMessage.DELIVERY_NOT_FOUND_ERROR_RESPONSE)
        .onErrorReturn(Exception.class, "error");

  }

  public Mono<String> end(TelegramMessage telegramMessage) {
    return deliveryService.end(
        telegramMessage.getChat().getId(),
        telegramMessage.getFrom().getId()
    ).map(delivery -> delivery.getUsers()
        .values()
        .stream()
        .flatMap(userInfo -> userInfo.getMenus()
            .entrySet()
            .stream()
        )
        .collect(Collectors.groupingBy(Map.Entry::getKey, summingInt(Map.Entry::getValue)))
        .entrySet()
        .stream()
        .map(entry -> entry.getKey() + " x " + entry.getValue())
        .collect(Collectors.joining("\n"))
    )
        .onErrorReturn(NotFoundDeliveryException.class, ResponseMessage.DELIVERY_NOT_FOUND_ERROR_RESPONSE)
        .onErrorReturn(NotDeliveryOwnerException.class, ResponseMessage.ONLY_OWNER_CAN_END_DELIVERY_ERROR_RESPONSE);
  }
}