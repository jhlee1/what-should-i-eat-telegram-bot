package lee.joohan.whattoeattelegrambot.domain;

import java.util.Collection;
import java.util.Map;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

/**
 * Created by Joohan Lee on 2020/05/22
 */
public class GoogleOAuthUserInfo extends OAuthUserInfo {

  public GoogleOAuthUserInfo(Map<String, Object> attributes) {
    super(attributes);
  }

  @Override
  protected CommonOAuth2Provider getAuthProviderEnum() {
    return CommonOAuth2Provider.GOOGLE;
  }

  @Override
  protected void setAttribute() {
    this.name = String.valueOf(String.valueOf(attributes.get("sub")));
    this.nickName = String.valueOf(attributes.get("name"));
    this.email = String.valueOf(attributes.get("email"));
    this.image = String.valueOf(attributes.get("picture"));
  }

  @Override
  public Map<String, Object> getClaims() {
    return attributes;
  }

  @Override
  public OidcUserInfo getUserInfo() {
    return null;
  }

  @Override
  public OidcIdToken getIdToken() {
    return null;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }
}
