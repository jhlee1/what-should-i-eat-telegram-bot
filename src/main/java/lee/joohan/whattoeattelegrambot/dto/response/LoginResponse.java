package lee.joohan.whattoeattelegrambot.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by Joohan Lee on 2020/05/18
 */

@NoArgsConstructor
@Getter
public class LoginResponse {
  private String token;

  public LoginResponse(String token) {
    this.token = token;
  }
}
