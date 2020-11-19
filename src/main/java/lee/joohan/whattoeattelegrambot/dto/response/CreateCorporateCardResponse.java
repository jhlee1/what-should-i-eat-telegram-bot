package lee.joohan.whattoeattelegrambot.dto.response;

import lee.joohan.whattoeattelegrambot.domain.CorporateCard;
import lombok.Getter;

/**
 * Created by Joohan Lee on 2020/08/28
 */

@Getter
public class CreateCorporateCardResponse {
  private int cardNum;
  private boolean isBorrowed;

  public CreateCorporateCardResponse(CorporateCard corporateCard) {
    cardNum = corporateCard.getCardNum();
    isBorrowed = corporateCard.isBorrowed();
  }
}
