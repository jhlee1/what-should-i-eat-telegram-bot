package lee.joohan.whattoeattelegrambot.exception;

/**
 * Created by Joohan Lee on 2020/07/21
 */
public class NotDeliveryOwnerException extends RuntimeException {
  public NotDeliveryOwnerException() {
    super("Only the creator can close the delivery");
  }
}
