package lee.joohan.whattoeattelegrambot.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Created by Joohan Lee on 2020/05/31
 */


@SpringBootTest
@ExtendWith(SpringExtension.class)
class GoogleOAuthClientTest {
  @Autowired
  GoogleOAuthClient googleOAuthClient;

  private final String accessToken = "whatever";

  @Test
  void getUserInfoProfile() {
    System.out.println(googleOAuthClient.getUserInfoProfile(accessToken).block());
  }
}