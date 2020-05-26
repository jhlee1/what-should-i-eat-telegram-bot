package lee.joohan.whattoeattelegrambot.config.oauth;

import java.util.Map;
import lee.joohan.whattoeattelegrambot.domain.User;
import lombok.Builder;
import lombok.Getter;

/**
 * Created by Joohan Lee on 2020/05/26
 */

@Getter
public class OAuthAttributes {
  private Map<String, Object> attributes;
  private String nameAttributeKey;
  private String name;
  private String email;
  private String picture;

  @Builder
  public OAuthAttributes(
      Map<String, Object> attributes,
      String nameAttributeKey,
      String name,
      String email,
      String picture) {
    this.attributes = attributes;
    this.nameAttributeKey = nameAttributeKey;
    this.name = name;
    this.email = email;
    this.picture = picture;
  }

  public static OAuthAttributes of(String registrationId, String usernameAttributeName, Map<String, Object> attributes) {
    return ofGoogle(usernameAttributeName, attributes);
  }

  private static OAuthAttributes ofGoogle(String usernameAttributeName, Map<String, Object> attributes) {
    return OAuthAttributes.builder()
        .name((String) attributes.get("name"))
        .email((String) attributes.get("email"))
        .picture((String) attributes.get("picture"))
        .attributes(attributes)
        .nameAttributeKey(usernameAttributeName)
        .build();
  }

  public User toEntity() {
    return User.builder()
        .email(email)
        .picture(picture)
        .build();

  }
}
