package lee.joohan.whattoeattelegrambot.exception;

/**
 * Created by Joohan Lee on 2020/02/16
 */
public class AlreadyExistRestaurantException extends RuntimeException {
  private AlreadyExistRestaurantException(String message) {
    super(message);
  }

  public static AlreadyExistRestaurantException fromName(String name) {
    return new AlreadyExistRestaurantException(String.format("The restaurant[name: %s] already exists.", name));
  }
}
