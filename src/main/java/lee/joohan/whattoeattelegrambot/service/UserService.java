package lee.joohan.whattoeattelegrambot.service;

import lee.joohan.whattoeattelegrambot.domain.User;
import lee.joohan.whattoeattelegrambot.exception.AlreadyVerifiedEmailException;
import lee.joohan.whattoeattelegrambot.exception.AlreadyVerifiedTelegramIdException;
import lee.joohan.whattoeattelegrambot.exception.NotFoundUserException;
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

  @Transactional
  public Mono<User> getOrRegister(Mono<User> userMono) {
    return userMono
        .map(User::getTelegramId)
        .<User>flatMap(userRepository::findByTelegramId)
        .switchIfEmpty(userMono.flatMap(userRepository::save));
  }

  @Transactional
  public Mono<User> register(User user) {
    return userRepository.save(user);
  }

  @Transactional(readOnly = true)
  public Mono<User> findByEmail(Mono<String> emailMono) {
    return emailMono.flatMap(userRepository::findByEmail);
  }

  @Transactional
  public Mono<User> verifyTelegram(String email, long telegramId, String firstName, String lastName) {
    return userRepository.findByTelegramId(telegramId)
        .<User>flatMap(user -> Mono.error(AlreadyVerifiedTelegramIdException.from(user.getId(), user.getTelegramId())))
        .switchIfEmpty(
            userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(NotFoundUserException.fromTelegramId(telegramId)))
                .flatMap(user -> {
                      if (user.isTelegramVerified()) {
                        return Mono.error(AlreadyVerifiedEmailException.from(email, telegramId));
                      }

                      user.verifyTelegram(telegramId, lastName, firstName);

                      return userRepository.save(user);
                    }
                )
        );
  }
}
