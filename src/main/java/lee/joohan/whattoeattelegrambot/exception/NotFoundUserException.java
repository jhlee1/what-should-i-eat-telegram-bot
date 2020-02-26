package lee.joohan.whattoeattelegrambot.exception;

/**
 * Created by Joohan Lee on 2020/02/15
 */
public class NotFoundUserException extends RuntimeException {

  private NotFoundUserException(String message) {
    super(message);
  }

  public static NotFoundUserException fromTelegramId(long telegramId) {
    return new NotFoundUserException(String.format("The user with telegramId[%s] does not exist.", telegramId));
  }
}
