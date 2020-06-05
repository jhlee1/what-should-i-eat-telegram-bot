package lee.joohan.whattoeattelegrambot.exception;

/**
 * Created by Joohan Lee on 2020/06/05
 */
public class NotValidEmailException extends RuntimeException {
  public NotValidEmailException(String email) {
    super(String.format("The email[%s] is not an ogq email", email));
  }

}
