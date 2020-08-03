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
  private String address; //TODO: Geospatial로 바꾸기
  private List<Review> reviews;
  private List<Menu> menus;
  private ObjectId creatorId;
  private ObjectId updaterId;

  @CreatedDate
  private LocalDateTime createdAt;
  @LastModifiedDate
  private LocalDateTime updatedAt;


  @Builder
  public Restaurant(String name, String address, ObjectId creatorId) {
    this.name = name;
    this.address = address;
    this.creatorId = creatorId;

    reviews = new ArrayList<>();
    menus = new ArrayList<>();
  }

  public void addMenu(Menu menu) {
    menus.add(menu);
  }

  public void changeName(String to, ObjectId updaterId) {
    name = to;
    this.updaterId = updaterId;
  }
}
