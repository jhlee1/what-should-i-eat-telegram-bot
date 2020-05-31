package lee.joohan.whattoeattelegrambot.dto.response;

import lee.joohan.whattoeattelegrambot.domain.dao.CorporateCardStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by Joohan Lee on 2020/05/15
 */

@ToString
@Getter
@NoArgsConstructor
public class CorporateCardStatusResponse {
  private boolean isBorrowed;
  private int cardNum;
  private String name;

  public CorporateCardStatusResponse(CorporateCardStatus corporateCardStatus) {
    isBorrowed = corporateCardStatus.isBorrowed();
    cardNum = corporateCardStatus.getCardNum();
    name = corporateCardStatus.getUserInfo().getFullName();
  }
}
