package lee.joohan.whattoeattelegrambot.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Joohan Lee on 2020/04/07
 */



@Getter
@Document("corporate_card_usage_log")
@ToString
public class CorporateCardUsageLog {
    private ObjectId userId;
    private CardAction cardAction;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public CorporateCardUsageLog(ObjectId userId, CardAction cardAction) {
        this.userId = userId;
        this.cardAction = cardAction;
        this.createdAt = LocalDateTime.now();
    }
}
