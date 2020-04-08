package lee.joohan.whattoeattelegrambot.domain;

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

  @Builder
  public User(long telegramId, String lastName, String firstName) {
    this.telegramId = telegramId;
    this.lastName = lastName;
    this.firstName = firstName;
  }

  public String getFullName() {
    return lastName + firstName;
  }
}
