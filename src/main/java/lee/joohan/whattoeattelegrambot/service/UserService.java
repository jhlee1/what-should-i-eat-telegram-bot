package lee.joohan.whattoeattelegrambot.service;

import lee.joohan.whattoeattelegrambot.domain.User;
import lee.joohan.whattoeattelegrambot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/02/15
 */

@RequiredArgsConstructor
@Service
public class UserService {
  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  public Mono<User> get(Mono<Long> telegramIdMono) {
    return telegramIdMono.flatMap(userRepository::findByTelegramId);
  }

  @Transactional
  public Mono<User> getOrRegister(Mono<User> userMono) {
//    return userRepository.findByTelegramId(userMono.);
    return userMono
            .map(User::getTelegramId)
            .<User>flatMap(userRepository::findByTelegramId)
            .switchIfEmpty(userMono.flatMap(userRepository::save));
  }

  @Transactional
  public Mono<User> register(Mono<User> userMono) {
    return userMono.flatMap(userRepository::save);
  }

  @Transactional(readOnly = true)
  public Mono<User> findByEmail(Mono<String> emailMono) {
    return emailMono.flatMap(userRepository::findByEmail);
  }
}
