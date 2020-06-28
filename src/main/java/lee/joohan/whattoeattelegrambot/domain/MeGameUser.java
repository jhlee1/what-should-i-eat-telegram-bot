package lee.joohan.whattoeattelegrambot.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * Created by Joohan Lee on 2020/06/18
 */

@Getter
public class MeGameUser {
  @Id
  private ObjectId id;

  private long telegramId;
  private String lastName;
  private String firstName;

  @CreatedDate
  LocalDateTime createdAt;

  @LastModifiedDate
  LocalDateTime updatedAt;

  @Builder
  public MeGameUser(long telegramId, String lastName, String firstName) {
    this.telegramId = telegramId;
    this.lastName = lastName;
    this.firstName = firstName;
  }
}
