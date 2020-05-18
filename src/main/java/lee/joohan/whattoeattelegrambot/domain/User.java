package lee.joohan.whattoeattelegrambot.domain;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Joohan Lee on 2020/02/15
 */

@Getter
@Document("user")
public class User {
  @Id
  private ObjectId id;

  private long telegramId;
  private String lastName;
  private String firstName;
  private String username;
  private String password;
  private List<UserRole> roles;

  @Builder
  public User(long telegramId, String lastName, String firstName, String username) {
    this.telegramId = telegramId;
    this.lastName = lastName;
    this.firstName = firstName;
    this.username = username;
  }

  public String getFullName() {
    return new StringBuilder()
        .append(lastName)
        .append(firstName)
        .toString();
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
