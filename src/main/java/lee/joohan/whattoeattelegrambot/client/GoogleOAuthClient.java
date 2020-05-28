package lee.joohan.whattoeattelegrambot.client;


import java.lang.reflect.ParameterizedType;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class GoogleOAuthClient {
  private WebClient webClient;

  public GoogleOAuthClient() {
    webClient = WebClient.create("https://www.googleapis.com/auth");
  }

  public Mono<Map<String, Object>> getUserInfoProfile(String token) {
    return webClient.get()
        .uri("/auth/userinfo.profile")
        .header("Authorization", "Bearer " + token)
        .retrieve()
    .bodyToMono(new ParameterizedTypeReference<>() {});

  }
}
