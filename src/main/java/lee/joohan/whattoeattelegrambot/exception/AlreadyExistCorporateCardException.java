package lee.joohan.whattoeattelegrambot.exception;

/**
 * Created by Joohan Lee on 2020/08/28
 */
public class AlreadyExistCorporateCardException extends RuntimeException {
  public AlreadyExistCorporateCardException(int cardNum) {
    super(String.format("The corporate card [number: %s] already exits.", cardNum));
  }
}
