package lee.joohan.whattoeattelegrambot.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

/**
 * Created by Joohan Lee on 2020/05/15
 */

@Getter
@NoArgsConstructor
public class UseCorporateCardRequest {
  private int cardNum;
  private ObjectId userId;
}
