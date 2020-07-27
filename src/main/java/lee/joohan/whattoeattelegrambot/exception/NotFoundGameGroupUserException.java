package lee.joohan.whattoeattelegrambot.exception;

/**
 * Created by Joohan Lee on 2020/07/27
 */
public class NotFoundGameGroupUserException extends RuntimeException {

  public NotFoundGameGroupUserException(String name) {
    super(String.format("The game group member [name: %s] is not found", name));
  }
}
