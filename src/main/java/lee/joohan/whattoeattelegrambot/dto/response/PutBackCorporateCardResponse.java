package lee.joohan.whattoeattelegrambot.dto.response;

import lee.joohan.whattoeattelegrambot.domain.CorporateCard;
import lombok.Getter;

/**
 * Created by Joohan Lee on 2020/05/15
 */

@Getter
public class PutBackCorporateCardResponse {
  private boolean isReturned;

  public PutBackCorporateCardResponse(CorporateCard corporateCard) {
    isReturned = !corporateCard.isBorrowed();
  }
}
