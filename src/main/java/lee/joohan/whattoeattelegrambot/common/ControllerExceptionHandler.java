package lee.joohan.whattoeattelegrambot.common;

import lee.joohan.whattoeattelegrambot.exception.AlreadyExistRestaurantException;
import lee.joohan.whattoeattelegrambot.exception.NotFoundFoodException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by Joohan Lee on 2020/02/15
 */

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(NotFoundFoodException.class)
  protected String handleNotFoundFoodException(NotFoundFoodException e) {
    return e.getMessage();
  }

  @ExceptionHandler(AlreadyExistRestaurantException.class)
  protected String handleAlreadyExistRestaurantException(AlreadyExistRestaurantException e) {
    return e.getMessage();
  }
}
