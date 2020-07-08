package lee.joohan.whattoeattelegrambot.service;

import lee.joohan.whattoeattelegrambot.domain.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LoggerGroup;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/05/19
 */

@ActiveProfiles("test")
@Testcontainers
@SpringBootTest
class UserServiceTest {

  private static Logger LOGGER = LoggerFactory.getLogger(UserServiceTest.class);

  @Autowired
  UserService userService;

  @Container
  static GenericContainer mongoDBContainer = new GenericContainer("mongo")
      .withExposedPorts(27017)
      .withEnv("MONGO_INITDB_ROOT_USERNAME", "example")
      .withEnv("MONGO_INITDB_ROOT_PASSWORD", "password");

  @BeforeAll
  static void beforeAll() {
    Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(LOGGER);

    mongoDBContainer.followOutput(logConsumer);
    mongoDBContainer.start();
  }

  @AfterAll
  static void afterAll() {
    mongoDBContainer.stop();
  }

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