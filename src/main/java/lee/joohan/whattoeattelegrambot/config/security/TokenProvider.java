package lee.joohan.whattoeattelegrambot.config.security;

import static lee.joohan.whattoeattelegrambot.common.AccessTokenKey.USER_ID_KEY;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Joohan Lee on 2020/05/18
 */

@Component
public class TokenProvider implements Serializable {
  @Value("${accessToken.secretKey}")
  private String secretKey;
  @Value("${accessToken.validForSeconds}")
  private long tokenValidForSeconds;

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
        .setSigningKey(secretKey)
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
        .claim(USER_ID_KEY, user.getId().toHexString())
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .setIssuer("anjajrqht")
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + tokenValidForSeconds * 1000))
        .compact();
  }
}
