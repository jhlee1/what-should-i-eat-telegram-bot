package lee.joohan.whattoeattelegrambot.exception;

/**
 * Created by Joohan Lee on 2020/05/31
 */
public class EmailNotVerifiedException extends RuntimeException {

  public EmailNotVerifiedException(String email) {
    super(String.format("The email[%s] is not verified from Google"));
  }


}
