package lee.joohan.whattoeattelegrambot.domain;

import java.util.ArrayList;
import java.util.List;
import lee.joohan.whattoeattelegrambot.exception.AlreadyExistingGameGroupUserException;
import lee.joohan.whattoeattelegrambot.exception.NotFoundGameGroupUserException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Joohan Lee on 2020/07/24
 */


@Document("ladder_game_group")
@Getter
@NoArgsConstructor
public class LadderGameGroup {
  @Id
  private ObjectId id;

  private List<String> users;
  private String name;

  public LadderGameGroup(String name) {
    this.name = name;
    users = new ArrayList<>();
  }

  public void setUsers(List<String> users) {
    this.users = users;
  }

  public void addUser(String username) {
    if (users.contains(username)) {
      throw new AlreadyExistingGameGroupUserException(username);
    }

    users.add(username);
  }

  public void removeUser(String username) {
    if (!users.contains(username)) {
      throw new NotFoundGameGroupUserException(username);
    }

    users.remove(username);
  }
}
