package lee.joohan.whattoeattelegrambot.domain;

import java.security.AuthProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.management.relation.Role;

/**
 * Created by Joohan Lee on 2020/02/15
 */

@Getter
@Document("user")
public class User {
  @Id
  private ObjectId id;

  private long telegramId;
  private String lastName;
  private String firstName;
  private String email;
  private String password;
  private List<UserRole> roles;

  //TODO: 필요한 부분일까
  private SocialAuthProvider provider;
  private String providerId;

  @Builder
  public User(long telegramId, String lastName, String firstName, String email, String password) {
    this.telegramId = telegramId;
    this.lastName = lastName;
    this.firstName = firstName;
    this.email = email;
    this.password = password;
    this.roles = Arrays.asList(UserRole.ROLE_USER);
  }

  public String getFullName() {
    return new StringBuilder()
        .append(lastName)
        .append(firstName)
        .toString();
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
