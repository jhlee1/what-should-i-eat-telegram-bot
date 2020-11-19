package lee.joohan.whattoeattelegrambot.domain;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Joohan Lee on 2020/06/18
 */

@Document("me_game")
@Getter
@NoArgsConstructor
public class MeGame {
  @Id
  private ObjectId id;

  @CreatedDate
  LocalDateTime createdAt;

  @LastModifiedDate
  LocalDateTime updatedAt;

  private long chatRoomId;
  private MeGameUser owner;
  private Map<ObjectId, MeGameUser> players;
  private MeGameStatus meGameStatus;

  @Builder
  public MeGame(long chatRoomId, MeGameUser owner) {
    this.chatRoomId = chatRoomId;
    this.owner = owner;
  }
}
