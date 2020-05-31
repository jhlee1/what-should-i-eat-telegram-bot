package lee.joohan.whattoeattelegrambot.dto.response;

import lee.joohan.whattoeattelegrambot.domain.CorporateCard;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by Joohan Lee on 2020/05/15
 */

@ToString
@Getter
@NoArgsConstructor
public class UseCorporateCardResponse {
  private boolean isBorrowed;

  public UseCorporateCardResponse(CorporateCard corporateCard) {
    isBorrowed = corporateCard.isBorrowed();
  }
}
