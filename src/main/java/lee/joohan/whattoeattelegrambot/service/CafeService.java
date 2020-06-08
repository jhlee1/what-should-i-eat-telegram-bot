package lee.joohan.whattoeattelegrambot.service;

import lee.joohan.whattoeattelegrambot.domain.Cafe;
import lee.joohan.whattoeattelegrambot.domain.User;
import lee.joohan.whattoeattelegrambot.exception.AlreadyExistCafeException;
import lee.joohan.whattoeattelegrambot.repository.CafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/04/27
 */

@Service
@RequiredArgsConstructor

public class CafeService {
  private final CafeRepository cafeRepository;

  @Transactional
  public Mono<Cafe> register(User user, String cafeName) {
    return cafeRepository.findByName(cafeName)
        .<Cafe>flatMap(cafe -> Mono.error(AlreadyExistCafeException.fromName(cafe.getName())))
        .switchIfEmpty(
            cafeRepository.save(
                Cafe.builder()
                    .name(cafeName)
                    .creatorId(user.getId())
                    .build()
            )
        );
  }

  public Flux<Cafe> getAll() {
    return cafeRepository.findAll();
  }
}
