package lee.joohan.whattoeattelegrambot.exception;

/**
 * Created by Joohan Lee on 2020/05/20
 */
public class AlreadyVerifiedEmailException extends RuntimeException {
  private AlreadyVerifiedEmailException(String message) {
    super(message);
  }

  public static AlreadyVerifiedEmailException from(String email, long telegramId) {
    return new AlreadyVerifiedEmailException(String.format("The user with email[%s] is already verified by through telegramId[%d]", email, telegramId));
  }
}
