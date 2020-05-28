package lee.joohan.whattoeattelegrambot.config.oauth;

import lee.joohan.whattoeattelegrambot.domain.OAuthUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.ReactiveOAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/05/26
 */

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomReactiveOAuth2ClientService implements ReactiveOAuth2UserService<OAuth2UserRequest, OAuth2User> {
  private final OAuthUserInfoRepository oAuthUserInfoRepository;

  @Override
  public Mono<OAuth2User> loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    final DefaultReactiveOAuth2UserService delegate = new DefaultReactiveOAuth2UserService();
    final String clientRegistrationId = userRequest.getClientRegistration().getRegistrationId();

    return delegate.loadUser(userRequest)
        .flatMap(e -> {
          OAuthUserInfo oAuth2UserInfo = OAuthUserInfoFactory.getOAuthUserInfo(clientRegistrationId, e.getAttributes());

          return oAuthUserInfoRepository
              .findByEmail(oAuth2UserInfo.getEmail())
              .switchIfEmpty(Mono.defer(() -> oAuthUserInfoRepository.save(oAuth2UserInfo)));
        });
  }
}
