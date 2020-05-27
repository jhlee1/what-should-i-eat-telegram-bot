package lee.joohan.whattoeattelegrambot.domain;

import java.io.Serializable;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

/**
 * Created by Joohan Lee on 2020/05/22
 */
@Document(collection = "userOAuthInfo")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class OAuthUserInfo implements OidcUser, Serializable {
  @MongoId
  protected ObjectId id;

  protected String name;

  protected String email;

  protected String nickName;

  protected String authProvider;

  protected String image;

  protected Map<String, Object> attributes;

  public OAuthUserInfo(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  protected abstract void setAttribute();
  protected abstract CommonOAuth2Provider getAuthProviderEnum();
}