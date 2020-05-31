package lee.joohan.whattoeattelegrambot.config.security;

import java.util.Collection;
import javax.security.auth.Subject;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Joohan Lee on 2020/05/20
 */
public class AccessToken extends AbstractAuthenticationToken {

  private String userId;
  private String email;

  public AccessToken(Collection<? extends GrantedAuthority> authorities, String userId, String email) {
    super(authorities);
    this.userId = userId;
    this.email = email;
    super.setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {
    return userId;
  }

  @Override
  public Object getPrincipal() {
    return email;
  }

  @Override
  public boolean implies(Subject subject) {
    return false;
  }
}
