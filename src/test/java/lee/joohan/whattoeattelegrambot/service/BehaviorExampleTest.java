package lee.joohan.whattoeattelegrambot.service;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/07/23
 */


class BehaviorExampleTest {

  @Test
  void OptionalOrElse() {
    System.out.println(Optional.ofNullable("hello")
        .orElse(hello()));

    System.out.println(Optional.ofNullable("hello")
        .orElseGet(() -> hello()));
  }

  @Test
  void UsingMono() {
    Mono.just(hello())
        .doOnNext(it -> System.out.println(it));

  }

  private String hello() {
    System.out.println("Executed!");
    return "hello";
  }
}