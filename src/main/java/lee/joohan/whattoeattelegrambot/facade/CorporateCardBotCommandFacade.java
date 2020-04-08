package lee.joohan.whattoeattelegrambot.facade;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lee.joohan.whattoeattelegrambot.common.ResponseMessage;
import lee.joohan.whattoeattelegrambot.domain.CorporateCard;
import lee.joohan.whattoeattelegrambot.domain.User;
import lee.joohan.whattoeattelegrambot.domain.dao.CorporateCardStatus;
import lee.joohan.whattoeattelegrambot.service.CorporateCardService;
import lee.joohan.whattoeattelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Created by Joohan Lee on 2020/04/03
 */

@Component
@RequiredArgsConstructor
public class CorporateCardBotCommandFacade {
  private final CorporateCardService corporateCardService;
  private final UserService userService;

  public String useCard(Message message) {
    if (!Pattern.matches("/\\S+ \\d+", message.getText())) {
      return ResponseMessage.USE_CORPORATE_CARD_ARGS_ERROR_RESPONSE;
    }

    int cardNum = Integer.parseInt(message.getText().split(" ")[1]);

    User user = userService.getOrRegister(
        message.getFrom().getId(),
        message.getFrom().getLastName(),
        message.getFrom().getFirstName()
    );

    corporateCardService.use(cardNum, user.getId());

    return ResponseMessage.USE_CORPORATE_CARD;
  }

  public String putBackCard(Message message) {
    if (Pattern.matches("/\\S+", message.getText())) {
      User user = userService.get(message.getFrom().getId());

      corporateCardService.putBack(user.getId());
    } else {
      if (!Pattern.matches("/\\S+ \\d+", message.getText())) {
        return ResponseMessage.PUT_BACK_CORPORATE_CARD_ARGS_ERROR_RESPONSE;
      }

      int cardNum = Integer.parseInt(message.getText().split(" ")[1]);

      User user = userService.getOrRegister(
          message.getFrom().getId(),
          message.getFrom().getLastName(),
          message.getFrom().getFirstName()
      );

      corporateCardService.putBack(cardNum, user.getId());
    }

    return ResponseMessage.RETURN_CORPORATE_CARD;
  }

  public String listCards() {
    return corporateCardService.listCardStatuses().stream()
        .map(cardStatus -> String.format("카드 번호: %s, 사용자: %s, 반납여부: %s", cardStatus.getCardNum(), cardStatus.getUserInfo().getFullName(), !cardStatus.isBorrowed()))
        .collect(Collectors.joining("\n"));
  }
}
