package lee.joohan.whattoeattelegrambot.common;

import static lee.joohan.whattoeattelegrambot.common.AccessTokenKey.AUTHORITY_KEY;

import io.jsonwebtoken.Claims;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
  @SuppressWarnings("unchecked")
  public Mono authenticate(Authentication authentication) {
    String authToken = authentication.getCredentials().toString();
    String username;
    try {
      username = tokenProvider.getUsernameFromToken(authToken);
    } catch (Exception e) {
      username = null;
    }
    if (username != null && ! tokenProvider.isTokenExpired(authToken)) {
      Claims claims = tokenProvider.getAllClaimsFromToken(authToken);
      List roles = claims.get(AUTHORITY_KEY, List.class)
      List authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(
          Collectors.toList());
      UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, username, authorities);
      SecurityContextHolder
          .getContext().setAuthentication(new AuthenticatedUser(username, authorities));
      return Mono.just(auth);
    } else {
      return Mono.empty();
    }
  }
}

//  https://www.devglan.com/spring-security/spring-security-webflux-jwt

//

