package lee.joohan.whattoeattelegrambot.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

import java.util.stream.Collectors;
import lee.joohan.whattoeattelegrambot.common.AccessTokenKey;
import lee.joohan.whattoeattelegrambot.domain.User;
import lee.joohan.whattoeattelegrambot.domain.UserRole;
import org.springframework.stereotype.Component;

/**
 * Created by Joohan Lee on 2020/05/18
 */

@Component
public class TokenProvider implements Serializable {
  private static final String SIGNING_KEY = "ajnajrqhtxptmxmwnd"; //TODO: Resource로 옮기기
  private static final long ACCESS_TOKEN_VALIDITY_SECONDS = 3600;

  public String getEmailFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  public Claims getAllClaimsFromToken(String token) {
    return Jwts.parser()
        .setSigningKey(SIGNING_KEY)
        .parseClaimsJws(token)
        .getBody();
  }

  public Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  public String generateToken(User user) {
    return Jwts.builder()
        .setSubject(user.getEmail())
        .claim(AccessTokenKey.AUTHORITY_KEY, user.getRoles()
            .stream()
            .map(UserRole::name)
            .collect(Collectors.toList()))
        .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
        .setIssuer("anjajrqht")
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
        .compact();
  }
}
