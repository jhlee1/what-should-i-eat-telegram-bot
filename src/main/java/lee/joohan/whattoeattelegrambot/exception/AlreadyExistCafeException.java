package lee.joohan.whattoeattelegrambot.exception;

/**
 * Created by Joohan Lee on 2020/02/16
 */
public class AlreadyExistCafeException extends RuntimeException {
  private AlreadyExistCafeException(String message) {
    super(message);
  }

  public static AlreadyExistCafeException fromName(String name) {
    return new AlreadyExistCafeException(String.format("The cafe[name: %s] already exists.", name));
  }
}
