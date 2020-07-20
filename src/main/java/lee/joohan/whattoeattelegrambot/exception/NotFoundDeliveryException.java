package lee.joohan.whattoeattelegrambot.exception;

/**
 * Created by Joohan Lee on 2020/07/21
 */

public class NotFoundDeliveryException extends RuntimeException {
  public NotFoundDeliveryException(long roomId) {
    super(String.format("There is no active delivery in the room [roomId: %s]", roomId));
  }
}
