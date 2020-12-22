package lee.joohan.whattoeattelegrambot.exception.delivery;

public class AlreadyExistDeliveryRestaurantException extends RuntimeException {
  private AlreadyExistDeliveryRestaurantException(String message) {
    super(message);
  }

  public static AlreadyExistDeliveryRestaurantException fromName(String name) {
    return new AlreadyExistDeliveryRestaurantException(String.format("The delivery restaurant[name: %s] already exists.", name));
  }
}
