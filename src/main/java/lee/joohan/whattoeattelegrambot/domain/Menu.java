package lee.joohan.whattoeattelegrambot.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * Created by Joohan Lee on 2020/02/15
 */

@NoArgsConstructor
@Getter
public class Menu {
  @Id
  private ObjectId id;

  private String name;
  private int price;
  private ObjectId creatorId;

  @CreatedDate
  private LocalDateTime createdAt;
  @LastModifiedDate
  private LocalDateTime updatedAt;

  @Builder
  public Menu(String name, int price, ObjectId creatorId) {
    this.name = name;
    this.price = price;
    this.creatorId = creatorId;
  }
}
