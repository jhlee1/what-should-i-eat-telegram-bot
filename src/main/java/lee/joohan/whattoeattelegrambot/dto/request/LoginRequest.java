package lee.joohan.whattoeattelegrambot.dto.request;

import lombok.Getter;

/**
 * Created by Joohan Lee on 2020/05/18
 */

@Getter
public class LoginRequest {
  private String email;
  private String password;
}
