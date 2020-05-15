package lee.joohan.whattoeattelegrambot.dto.request;

import lombok.Getter;
import org.bson.types.ObjectId;

/**
 * Created by Joohan Lee on 2020/05/15
 */

@Getter
public class PutBackCorporateCardRequest {
  private int cardNum;
  private ObjectId userId;
}
