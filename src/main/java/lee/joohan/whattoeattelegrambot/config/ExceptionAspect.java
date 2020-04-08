package lee.joohan.whattoeattelegrambot.config;

import lee.joohan.whattoeattelegrambot.exception.AlreadyExistRestaurantException;
import lee.joohan.whattoeattelegrambot.exception.NotFoundRestaurantException;
import lee.joohan.whattoeattelegrambot.exception.corporate_card.CorporateCardAlreadyInUseException;
import lee.joohan.whattoeattelegrambot.exception.corporate_card.CorporateCardAlreadyReturnedException;
import lee.joohan.whattoeattelegrambot.exception.corporate_card.NotBorrowedAnyCardException;
import lee.joohan.whattoeattelegrambot.exception.corporate_card.NotFoundCorporateCardException;
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
    } catch (CorporateCardAlreadyInUseException e) {
      return "이미 사용중인 카드입니다.";
    } catch (CorporateCardAlreadyReturnedException e) {
      return "이미 반납된 카드입니다.";
    } catch (NotFoundCorporateCardException e) {
      return "존재하지 않는 카드입니다.";
    } catch (NotBorrowedAnyCardException e) {
      return "가져간 카드가 없습니다.";
    }

    catch (Exception e) {
      log.error("Internal server error: ", e);
      return "알 수 없는 에러 발생";
    }
  }
}