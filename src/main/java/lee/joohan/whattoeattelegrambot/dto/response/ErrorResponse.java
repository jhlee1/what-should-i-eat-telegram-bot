package lee.joohan.whattoeattelegrambot.dto.response;

import lombok.Getter;

/**
 * Created by Joohan Lee on 2020/05/20
 */

@Getter
public class ErrorResponse {
  private String message;

  public ErrorResponse(String message, Object... params) {
    this.message = String.format(message, params);
  }
}
