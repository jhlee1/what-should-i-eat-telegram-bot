package lee.joohan.whattoeattelegrambot.dto.response;

import lombok.Getter;

/**
 * Created by Joohan Lee on 2020/05/20
 */

@Getter
public class ErrorResponse {
  private int code;
  private String message;

  public ErrorResponse(int errorCode, String message, Object... params) {
    code = errorCode;
    this.message = String.format(message, params);
  }
}
