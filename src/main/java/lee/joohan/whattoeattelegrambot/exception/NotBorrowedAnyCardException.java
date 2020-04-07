package lee.joohan.whattoeattelegrambot.exception;

/**
 * Created by Joohan Lee on 2020/04/07
 */
public class NotBorrowedAnyCardException extends RuntimeException {


  public NotBorrowedAnyCardException(String userId) {
    super(String.format("The user[userId: %s] has not borrowed any card."));
  }
}
