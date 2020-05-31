package lee.joohan.whattoeattelegrambot.client;


import lee.joohan.whattoeattelegrambot.dto.response.external.GoogleOAuthUserInfoResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class GoogleOAuthClient {
  private WebClient webClient;

  public GoogleOAuthClient() {
    webClient = WebClient.create("https://www.googleapis.com");
  }

  public Mono<GoogleOAuthUserInfoResponse> getUserInfoProfile(String token) {
    return webClient.get()
        .uri("oauth2/v2/userinfo")
        .header("Authorization", String.format("Bearer %s", token))
        .retrieve()
        .bodyToMono(GoogleOAuthUserInfoResponse.class);
  }
}
