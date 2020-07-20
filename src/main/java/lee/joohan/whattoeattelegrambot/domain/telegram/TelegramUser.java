package lee.joohan.whattoeattelegrambot.domain.telegram;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.User;

/**
 * Created by Joohan Lee on 2020/05/31
 */

@JsonInclude(Include.NON_EMPTY)
@Getter
public class TelegramUser {
  private long id;
  private String firstName;
  private String lastName;
  private String username;
  private String languageCode;

  public TelegramUser(User user) {
    id = user.getId();
    firstName = user.getFirstName();
    lastName = user.getLastName();
    username = user.getUserName();
    languageCode = user.getLanguageCode();
  }

  public String getFullName() {
    return lastName + firstName;
  }
}
