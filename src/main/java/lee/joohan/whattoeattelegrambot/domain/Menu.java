package lee.joohan.whattoeattelegrambot.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
  private List<Review> reviews;
  private User creator;

  @CreatedDate
  private LocalDateTime createdAt;
  @LastModifiedDate
  private LocalDateTime updatedAt;

  @Builder
  public Menu(String name, int price, User creator) {
    this.name = name;
    this.price = price;
    this.creator = creator;

    reviews = new ArrayList<>();
  }

  public void addReview(Review review) {
    reviews.add(review);
  }
}
