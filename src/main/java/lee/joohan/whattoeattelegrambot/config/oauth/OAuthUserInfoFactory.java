package lee.joohan.whattoeattelegrambot.config.oauth;

import java.util.Map;
import lee.joohan.whattoeattelegrambot.domain.GoogleOAuthUserInfo;
import lee.joohan.whattoeattelegrambot.domain.OAuthUserInfo;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;

public class OAuthUserInfoFactory {
  public static OAuthUserInfo getOAuthUserInfo(String registrationId, Map<String, Object> attributes) {
    if (registrationId.equalsIgnoreCase(CommonOAuth2Provider.GOOGLE.name())) {
      return new GoogleOAuthUserInfo(attributes);
    } else {
      throw new AssertionError(String.format("The provider[%s] is not supported", registrationId));
    }
  }

}
