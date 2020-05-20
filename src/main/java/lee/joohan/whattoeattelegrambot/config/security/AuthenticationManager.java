package lee.joohan.whattoeattelegrambot.config.security;

import static lee.joohan.whattoeattelegrambot.common.AccessTokenKey.AUTHORITY_KEY;
import static lee.joohan.whattoeattelegrambot.common.AccessTokenKey.USER_ID_KEY;

import io.jsonwebtoken.Claims;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/05/18
 */

@RequiredArgsConstructor
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {
  private final TokenProvider tokenProvider;

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    String authToken = authentication.getCredentials().toString();
    String email;
    try {
      email = tokenProvider.getEmailFromToken(authToken);
    } catch (Exception e) {
      email = null;
    }
    if (email != null && !tokenProvider.isTokenExpired(authToken)) {
      Claims claims = tokenProvider.getAllClaimsFromToken(authToken);
      List<String> roles = claims.get(AUTHORITY_KEY, List.class);
      List<SimpleGrantedAuthority> authorities = roles.stream()
          .map(role -> new SimpleGrantedAuthority(role))
          .collect(Collectors.toList());
      String userId = claims.get(USER_ID_KEY, String.class);

      AccessToken auth = new AccessToken(authorities, userId, email);
      SecurityContextHolder.getContext().setAuthentication(new AuthenticatedUser(email, authorities, userId));
      return Mono.just(auth);
    } else {
      return Mono.empty();
    }
  }
}

