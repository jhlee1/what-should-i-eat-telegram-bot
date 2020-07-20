package lee.joohan.whattoeattelegrambot.exception;

/**
 * Created by Joohan Lee on 2020/07/21
 */
public class AlreadyExistDeliveryException extends RuntimeException{
  public AlreadyExistDeliveryException(long roomId) {
    super(String.format("Active delivery already exists in the room [roomId: %s]", roomId));
  }


}
