package lee.joohan.whattoeattelegrambot.domain.delivery;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lee.joohan.whattoeattelegrambot.exception.NotDeliveryOwnerException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Joohan Lee on 2020/07/17
 */
@NoArgsConstructor
@Getter
@Document("delivery")
public class Delivery {
  @Id
  private ObjectId id;

  private long roomId;
  private DeliveryUserInfo host;
  private DeliveryStatus deliveryStatus;
  private Map<Long, DeliveryUserInfo> users;

  @CreatedDate
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  @Builder
  public Delivery(long roomId, String hostName, long hostTelegramId) {
    this.roomId = roomId;
    this.host = DeliveryUserInfo.builder()
        .name(hostName)
        .telegramId(hostTelegramId)
        .build();
    deliveryStatus = DeliveryStatus.STARTED;
    users = new HashMap<>();
  }

  public void addMenu(long telegramId, String username, String menuName, int menuNum) {
    DeliveryUserInfo userInfo = users.getOrDefault(
        telegramId,
        DeliveryUserInfo.builder()
            .telegramId(telegramId)
            .name(username)
            .build()
    );

    userInfo.addMenu(menuName, menuNum);

    users.putIfAbsent(telegramId, userInfo);
  }

  public void end(long telegramId) {
    if (telegramId != host.getTelegramId()) {
      throw new NotDeliveryOwnerException();
    }

    deliveryStatus = DeliveryStatus.FINISHED;
  }
}
