package lee.joohan.whattoeattelegrambot.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

/**
 * Created by Joohan Lee on 2020/05/15
 */

@Builder
@Getter
public class UseCorporateCardRequest {
  private int cardNum;
  private ObjectId userId;
}
