package lee.joohan.whattoeattelegrambot.exception;

/**
 * Created by Joohan Lee on 2020/07/27
 */
public class AlreadyExistingGameGroupUserException extends RuntimeException {
  public AlreadyExistingGameGroupUserException(String name) {
    super(String.format("The user[name: %s] already exists in the group", name));
  }
}
