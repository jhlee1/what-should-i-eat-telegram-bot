package lee.joohan.whattoeattelegrambot.exception.corporate_card;

/**
 * Created by Joohan Lee on 2020/04/08
 */
public class CorporateCardAlreadyReturnedException extends RuntimeException {

  public CorporateCardAlreadyReturnedException(int cardNum) {
    super(String.format("The card[cardNum: %d] is already returned.", cardNum));
  }
}
