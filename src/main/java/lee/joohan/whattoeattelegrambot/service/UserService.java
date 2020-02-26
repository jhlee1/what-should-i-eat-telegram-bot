package lee.joohan.whattoeattelegrambot.service;

import lee.joohan.whattoeattelegrambot.domain.User;
import lee.joohan.whattoeattelegrambot.exception.NotFoundUserException;
import lee.joohan.whattoeattelegrambot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Joohan Lee on 2020/02/15
 */

@RequiredArgsConstructor
@Service
public class UserService {
  private final UserRepository userRepository;

  public User register(long telegramId, String lastName, String firstName) {
    User user = User.builder()
        .telegramId(telegramId)
        .lastName(lastName)
        .firstName(firstName)
        .build();

    return userRepository.save(user);
  }

  @Transactional(readOnly = true)
  public User get(long telegramId) {
    return userRepository.findByTelegramId(telegramId)
        .orElseThrow(() -> NotFoundUserException.fromTelegramId(telegramId));
  }

  @Transactional
  public User getOrRegister(long telegramId, String lastName, String firstName) {
    return userRepository.findByTelegramId(telegramId)
        .orElseGet(() -> {
          User user = User.builder()
              .telegramId(telegramId)
              .lastName(lastName)
              .firstName(firstName)
              .build();

          return userRepository.save(user);
        });
  }
}
