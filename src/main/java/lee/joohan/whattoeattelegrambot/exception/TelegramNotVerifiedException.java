package lee.joohan.whattoeattelegrambot.exception;

import org.bson.types.ObjectId;

/**
 * Created by Joohan Lee on 2020/05/20
 */
public class TelegramNotVerifiedException extends RuntimeException {
  private TelegramNotVerifiedException(String message) {
    super(message);
  }

  public static TelegramNotVerifiedException fromUserId(ObjectId userId) {
    return new TelegramNotVerifiedException(String.format("The user with userID[%s] is not verified through telegram", userId.toHexString()));
  }
}
