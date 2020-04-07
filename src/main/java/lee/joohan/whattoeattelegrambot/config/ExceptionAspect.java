package lee.joohan.whattoeattelegrambot.config;

import lee.joohan.whattoeattelegrambot.exception.AlreadyExistRestaurantException;
import lee.joohan.whattoeattelegrambot.exception.NotFoundRestaurantException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by Joohan Lee on 2020/02/16
 */

@Component
@Aspect
@Slf4j
public class ExceptionAspect {

  @Around("@annotation(HandleException)")
  public Object handleException(ProceedingJoinPoint joinPoint) throws Throwable {
    try {
      return joinPoint.proceed();
    } catch (AlreadyExistRestaurantException e) {
      return "이미 등록된 식당입니다.";
    } catch (NotFoundRestaurantException e) {
      return "존재하지 않는 식당입니다.";
    }

    catch (Exception e) {
      log.error("Internal server error: ", e);
      return "알 수 없는 에러 발생";
    }
  }
}