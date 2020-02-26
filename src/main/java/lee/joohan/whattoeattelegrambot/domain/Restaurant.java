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
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Joohan Lee on 2020/02/15
 */

@Document("restaurant")
@Getter
@NoArgsConstructor
public class Restaurant {
  @Id
  private ObjectId id;

  private String name;
  private String address;
  private List<Review> reviews;
  private List<Menu> menus;
  private User creator;

  @CreatedDate
  private LocalDateTime createdAt;
  @LastModifiedDate
  private LocalDateTime updatedAt;


  @Builder
  public Restaurant(String name, String address, User creator) {
    this.name = name;
    this.address = address;
    this.creator = creator;
    reviews = new ArrayList<>();
    this.menus = new ArrayList<>();
  }

  public void addMenu(Menu menu) {
    menus.add(menu);
  }

  public void changeName(String to) {
    name = to;
  }
}
