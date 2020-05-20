package lee.joohan.whattoeattelegrambot.exception;

import org.bson.types.ObjectId;

/**
 * Created by Joohan Lee on 2020/05/20
 */
public class AlreadyVerifiedTelegramIdException extends RuntimeException {
  private AlreadyVerifiedTelegramIdException(String message) {
    super(message);
  }

  public static AlreadyVerifiedTelegramIdException from(ObjectId userId, long telegramId) {
    return new AlreadyVerifiedTelegramIdException(String.format("The telegramId [%s] is already used to verify the user with userId[%d]", telegramId, userId.toHexString()));
  }

}
