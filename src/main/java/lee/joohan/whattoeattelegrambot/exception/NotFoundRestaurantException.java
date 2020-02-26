package lee.joohan.whattoeattelegrambot.exception;

/**
 * Created by Joohan Lee on 2020/02/16
 */
public class NotFoundRestaurantException extends RuntimeException {

  private NotFoundRestaurantException(String message) {
    super(message);
  }

  public static NotFoundRestaurantException fromName(String name) {
    return new NotFoundRestaurantException(String.format("The restaurant with name[%s] does not exist.", name));
  }
}
