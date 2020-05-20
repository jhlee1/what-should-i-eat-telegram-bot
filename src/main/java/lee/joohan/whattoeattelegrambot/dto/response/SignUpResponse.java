package lee.joohan.whattoeattelegrambot.dto.response;

import lombok.Getter;

/**
 * Created by Joohan Lee on 2020/05/18
 */


@Getter
public class SignUpResponse {
  private boolean succeed;

  public SignUpResponse(boolean succeed) {
    this.succeed = succeed;
  }
}
