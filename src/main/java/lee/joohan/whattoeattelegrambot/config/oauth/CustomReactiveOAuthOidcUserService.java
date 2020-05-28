package lee.joohan.whattoeattelegrambot.config.oauth;

import lee.joohan.whattoeattelegrambot.domain.OAuthUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.ReactiveOAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomReactiveOAuthOidcUserService implements ReactiveOAuth2UserService<OidcUserRequest, OidcUser> {
  private final OAuthUserInfoRepository oAuthUserInfoRepository;

  @Override
  public Mono<OidcUser> loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
    final OidcReactiveOAuth2UserService delegate = new OidcReactiveOAuth2UserService();
    final String clientRegistrationId = userRequest.getClientRegistration().getRegistrationId();


    Mono<OidcUser> oAuthUser = delegate.loadUser(userRequest);
    return oAuthUser
        .flatMap(e -> {
      OAuthUserInfo oAuthUserInfo = OAuthUserInfoFactory.getOAuthUserInfo(clientRegistrationId, e.getAttributes());

      return oAuthUserInfoRepository
          .findByEmail(oAuthUserInfo.getEmail())
          .switchIfEmpty(Mono.defer(() -> oAuthUserInfoRepository.save(oAuthUserInfo)));
    });
  }
}