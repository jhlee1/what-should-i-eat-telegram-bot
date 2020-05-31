package lee.joohan.whattoeattelegrambot.dto.response.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by Joohan Lee on 2020/05/31
 */

@ToString
@Getter
public class GoogleOAuthUserInfoResponse {
  private String id;
  private String email;
  private String name;
  private String picture;
  private String locale;
  private String hd;

  @JsonProperty("verified_email")
  private boolean verifiedEmail;

  @JsonProperty("given_name")
  private String givenName;

  @JsonProperty("family_name")
  private String familyName;
}
