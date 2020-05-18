package lee.joohan.whattoeattelegrambot.dto.request;

import lombok.Getter;

@Getter
public class SignUpRequest {
    private String email;
    private String password;
}
