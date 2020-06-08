package lee.joohan.whattoeattelegrambot.domain;

import java.util.Arrays;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Joohan Lee on 2020/02/15
 */

@Getter
@NoArgsConstructor
@Document("user")
public class User {
  @Id
  private ObjectId id;

  private long telegramId;
  private String lastName;
  private String firstName;
  private String email;
  private List<UserRole> roles;
  private String picture;
  private boolean telegramVerified;

  @Builder
  public User(long telegramId, String lastName, String firstName, String email, String picture) {
    this.telegramId = telegramId;
    this.lastName = lastName;
    this.firstName = firstName;
    this.email = email;
    this.picture = picture;
    this.roles = Arrays.asList(UserRole.ROLE_USER);
  }

  public String getFullName() {
    return new StringBuilder()
        .append(lastName)
        .append(firstName)
        .toString();
  }

  public void verifyTelegram(long telegramId, String lastName, String firstName) {
    this.telegramId = telegramId;
    this.lastName = lastName;
    this.firstName = firstName;
    telegramVerified = true;
  }

  public boolean isAdmin() {
    return roles.stream()
        .anyMatch(UserRole.ROLE_ADMIN::equals);
  }
}
