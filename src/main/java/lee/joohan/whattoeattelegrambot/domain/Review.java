package lee.joohan.whattoeattelegrambot.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Joohan Lee on 2020/02/15
 */

@Document("review")
@Getter
@NoArgsConstructor
public class Review {
  @Id
  private ObjectId id;

  private String comment;
  private float rate;
  private ObjectId reviewerId;

  @CreatedDate
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  @Builder
  public Review(String comment, float rate, ObjectId reviewerId) {
    this.comment = comment;
    this.rate = Math.round(rate * 10) / 10f;
    this.reviewerId = reviewerId;
  }
}
