package lee.joohan.whattoeattelegrambot.domain;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Joohan Lee on 2020/03/12
 */

@Document("cafe")
@Getter
@NoArgsConstructor
public class Cafe {
  @Id
  private ObjectId id;

  @CreatedDate
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  private String name;
  private List<Menu> menus;
  private ObjectId creatorId;

  @Builder
  public Cafe(String name, ObjectId creatorId) {
    this.name = name;
    this.creatorId = creatorId;
  }
}
