package lee.joohan.whattoeattelegrambot.common;

/**
 * Created by Joohan Lee on 2020/06/11
 */
public class ErrorCode {
  public static final int CORPORATE_CARD_RETURN_FAILURE = 400001;
  public static final int TELEGRAM_NOT_VERIFIED = 400002;
  public static final int EMAIL_NOT_VERIFIED = 400003;
  public static final int NOT_BORROWED_ANY_CARD = 400004;
  public static final int NOT_VALID_EMAIL = 400005;


//  TelegramNotVerifiedException.class, error -> ServerResponse.badRequest().bodyValue(new ErrorResponse(error.getMessage())))
//        .onErrorResume(EmailNotVerifiedException.class, error -> ServerResponse.badRequest().bodyValue(new ErrorResponse(error.getMessage())))
//        .onErrorResume(NotValidEmailException

}
