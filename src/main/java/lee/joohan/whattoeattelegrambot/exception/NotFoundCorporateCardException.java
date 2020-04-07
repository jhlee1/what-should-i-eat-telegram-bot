package lee.joohan.whattoeattelegrambot.exception;

/**
 * Created by Joohan Lee on 2020/04/07
 */
public class NotFoundCorporateCardException extends RuntimeException {
  public NotFoundCorporateCardException(int cardNumber) {
    super(String.format("The card is not found. [Card Number: %s]", cardNumber));
  }
}
