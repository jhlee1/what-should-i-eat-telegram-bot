package lee.joohan.whattoeattelegrambot.exception.corporate_card;

/**
 * Created by Joohan Lee on 2020/04/08
 */
public class CorporateCardAlreadyInUseException extends RuntimeException {

  public CorporateCardAlreadyInUseException(int cardNum) {
    super(String.format("The card[cardNum: %d] is already in use.", cardNum));
  }
}
