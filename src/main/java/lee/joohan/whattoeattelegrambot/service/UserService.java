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
  public Mono<User> get(Mono<Long> telegramId) {
    return userRepository.findByTelegramId(telegramId);
  }

  @Transactional
  public Mono<User> getOrRegister(Mono<User> userMono) {
//    return userRepository.findByTelegramId(userMono.);
    return userRepository.findByTelegramId(userMono
        .map(User::getTelegramId))
        .flatMap(user -> userRepository.save(user));
  }
}
