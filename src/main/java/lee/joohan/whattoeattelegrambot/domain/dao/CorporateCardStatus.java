package lee.joohan.whattoeattelegrambot.domain.dao;

import lee.joohan.whattoeattelegrambot.domain.User;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by Joohan Lee on 2020/04/08
 */

@Getter
@ToString
public class CorporateCardStatus {
  private boolean isBorrowed;
  private int cardNum;

  private User userInfo;
}
