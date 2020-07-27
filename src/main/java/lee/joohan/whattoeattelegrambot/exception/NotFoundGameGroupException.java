package lee.joohan.whattoeattelegrambot.exception;

/**
 * Created by Joohan Lee on 2020/07/26
 */
public class NotFoundGameGroupException extends RuntimeException {

  public NotFoundGameGroupException(String name) {
    super(String.format("The game group with name[%s] is not found", name));
  }
}
