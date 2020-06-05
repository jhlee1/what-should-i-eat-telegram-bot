package lee.joohan.whattoeattelegrambot.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by Joohan Lee on 2020/05/18
 */

@NoArgsConstructor
@Getter
public class LoginResponse {
  private String token;
  private boolean admin;

  public LoginResponse(String token, boolean isAdmin) {
    this.token = token;
    admin = isAdmin;
  }
}
