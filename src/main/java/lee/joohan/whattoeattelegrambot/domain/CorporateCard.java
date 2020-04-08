package lee.joohan.whattoeattelegrambot.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lee.joohan.whattoeattelegrambot.exception.corporate_card.CorporateCardAlreadyInUseException;
import lee.joohan.whattoeattelegrambot.exception.corporate_card.CorporateCardAlreadyReturnedException;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
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
@ToString
public class CorporateCard {
  @Id
  ObjectId id;

  private int cardNum;
  private ObjectId currentUserId;
  private boolean isBorrowed;

  private List<CorporateCardUsageLog> usageLogs;

  @CreatedDate
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  public CorporateCard(int cardNum) {
    this.cardNum = cardNum;
    this.usageLogs = new ArrayList<>();
  }

  public void use(ObjectId userId) {
    if (isBorrowed) {
      throw new CorporateCardAlreadyInUseException(cardNum);
    }

    isBorrowed = true;
    currentUserId = userId;

    CorporateCardUsageLog corporateCardUsageLog = CorporateCardUsageLog.builder()
        .cardAction(CardAction.USE)
        .userId(userId)
        .build();

    usageLogs.add(corporateCardUsageLog);
  }

  public void putBack(ObjectId userId) {
    if (!isBorrowed) {
      throw new CorporateCardAlreadyReturnedException(cardNum);
    }

    isBorrowed = false;
    currentUserId = userId;

    CorporateCardUsageLog corporateCardUsageLog = CorporateCardUsageLog.builder()
        .cardAction(CardAction.RETURN)
        .userId(userId)
        .build();

    usageLogs.add(corporateCardUsageLog);
  }

  @ToString
  @Getter
  public static class CorporateCardUsageLog {
    private ObjectId userId;
    private CardAction cardAction;

    @CreatedDate // 따로 document를 생성하지 않아서 그런건지 안먹히넹...
    private LocalDateTime createdAt;

    @Builder
    public CorporateCardUsageLog(ObjectId userId, CardAction cardAction) {
      this.userId = userId;
      this.cardAction = cardAction;
      this.createdAt = LocalDateTime.now();
    }
  }
}
