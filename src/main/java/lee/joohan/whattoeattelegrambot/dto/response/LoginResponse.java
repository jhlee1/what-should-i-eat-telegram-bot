package lee.joohan.whattoeattelegrambot.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * Created by Joohan Lee on 2020/05/18
 */

@Getter
public class LoginResponse {
  private String token;


  @Builder
  public LoginResponse(String token) {
    this.token = token;
  }
}
