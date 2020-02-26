package lee.joohan.whattoeattelegrambot.exception;

/**
 * Created by Joohan Lee on 2020/02/15
 */


public class NotFoundFoodException extends RuntimeException {
  public NotFoundFoodException(String foodName) {
    super(String.format("The food is not found. [Name: %s]", foodName));
  }
}
