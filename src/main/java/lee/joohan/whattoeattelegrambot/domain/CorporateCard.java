package lee.joohan.whattoeattelegrambot.domain;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Joohan Lee on 2020/04/07
 */

@Getter
@Document("corporate_card")
public class CorporateCard {
  @Id
  ObjectId id;

  private int cardNum;
  private ObjectId currentUserId;
  private boolean isBorrowed;

  List<CorporateCardUsageLog> usageLogs;

  @CreatedDate
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  public CorporateCard(int cardNum) {
    this.cardNum = cardNum;
  }

  public void use(ObjectId userId) {
    isBorrowed = true;
    currentUserId = userId;

    CorporateCardUsageLog corporateCardUsageLog = CorporateCardUsageLog.builder()
        .cardAction(CardAction.USE)
        .userId(userId)
        .build();

    usageLogs.add(corporateCardUsageLog);
  }

  public void putBack(ObjectId userId) {
    isBorrowed = false;
    currentUserId = userId;

    CorporateCardUsageLog corporateCardUsageLog = CorporateCardUsageLog.builder()
        .cardAction(CardAction.RETURN)
        .userId(userId)
        .build();

    usageLogs.add(corporateCardUsageLog);
  }
}
