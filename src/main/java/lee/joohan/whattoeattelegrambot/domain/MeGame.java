package lee.joohan.whattoeattelegrambot.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
  private Map<Long, MeGameUser> players;
  private MeGameStatus meGameStatus;
  private int numOfRewards;
  private List<MeGameUser> winners;

  @Builder
  public MeGame(long chatRoomId, MeGameUser owner) {
    this.chatRoomId = chatRoomId;
    this.owner = owner;
    meGameStatus = MeGameStatus.STARTED;
    players = new HashMap<>();
  }

  public List<MeGameUser> end() {
    List<MeGameUser> meGameUsers = new ArrayList<>();
    Iterator<MeGameUser> iterator = players.values().iterator();

    for (int i = 0; i < numOfRewards; i++) {
      if (iterator.hasNext()) {
        meGameUsers.add(iterator.next());
      }
    }

    meGameStatus = MeGameStatus.END;

    return meGameUsers;
  }
}
