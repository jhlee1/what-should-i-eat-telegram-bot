package lee.joohan.whattoeattelegrambot.service;

import lee.joohan.whattoeattelegrambot.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/05/19
 */

@SpringBootTest
class UserServiceTest {
  @Autowired
  UserService userService;

  @Test
  void findByEmail() {
    User user = userService.findByEmail("joolee@ogqcorp.com").block();

    System.out.println("THe user: " + user);
  }

  @Test
  void register() {
    User user = User.builder().email("joolee@ogqcorp.com").build();

    System.out.println(userService.register(user).flatMap(it -> Mono.just("It has value in it")).switchIfEmpty(Mono.just("Empty")).block());
  }
}