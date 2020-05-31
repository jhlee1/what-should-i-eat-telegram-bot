package lee.joohan.whattoeattelegrambot.domain.telegram;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Chat;

/**
 * Created by Joohan Lee on 2020/05/31
 */

@Getter
@JsonInclude(Include.NON_EMPTY)
public class TelegramChat {
  private long id;
  private String title;
  private String firstName;
  private String lastName;
  private String userName;
  private String description;

  public TelegramChat(Chat chat) {
    id = chat.getId();
    description = chat.getDescription();
    title = chat.getTitle();
    firstName = chat.getFirstName();
    lastName = chat.getLastName();
    userName = chat.getUserName();
  }
}
