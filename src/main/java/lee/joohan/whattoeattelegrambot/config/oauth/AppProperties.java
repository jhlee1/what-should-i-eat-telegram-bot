package lee.joohan.whattoeattelegrambot.config.oauth;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "app")
public class AppProperties {
  private final Auth auth = new Auth();
  private final OAuth2 oAuth2 = new OAuth2();

  @Data
  public static final class Auth {
    private String tokenSecret;
    private long tokenExpirationMsec;
  }

  @Getter
  public static final class OAuth2 {
    private List<String> authorizedRedirectUris = new ArrayList<>();

    public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
      this.authorizedRedirectUris = authorizedRedirectUris;

      return this;
    }
  }
}

