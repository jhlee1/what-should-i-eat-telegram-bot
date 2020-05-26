package lee.joohan.whattoeattelegrambot.config.oauth;

import java.util.Collections;
import java.util.stream.Collectors;
import lee.joohan.whattoeattelegrambot.domain.User;
import lee.joohan.whattoeattelegrambot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.ReactiveOAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/05/26
 */

@RequiredArgsConstructor
@Service
public class CustomReactiveOAuth2ClientService implements ReactiveOAuth2UserService {
  private final UserRepository userRepository;

  @Override
  public Mono loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    ReactiveOAuth2UserService delegate = new DefaultReactiveOAuth2UserService();
    Mono<OAuth2User> oAuth2UserMono = delegate.loadUser(userRequest);

    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    String userNameAttributeName = userRequest.getClientRegistration()
        .getProviderDetails()
        .getUserInfoEndpoint()
        .getUserNameAttributeName();

    OAuthAttributes attributes = oAuth2UserMono
        .map(oauth2User -> OAuthAttributes.of(registrationId, userNameAttributeName, oauth2User.getAttributes()))
        .map(oAuthAttributes -> saveOrUpdate(oAuthAttributes))
        .map(user -> new DefaultOAuth2User(user.getRoles().stream()
        .map(Enum::name)
        .map(SimpleGrantedAuthority::new).collect(Collectors.toList()), user.a));

    User user = saveOrUpdate(attributes);

    return ;
  }


  private Mono<User> saveOrUpdate(OAuthAttributes oAuthAttributes) {
    return userRepository.findByEmail(oAuthAttributes.getEmail())
        .switchIfEmpty(Mono.fromSupplier(() -> oAuthAttributes.toEntity()))
        .flatMap(it -> userRepository.save(it));

  }
}
