package lee.joohan.whattoeattelegrambot.service;

import lee.joohan.whattoeattelegrambot.domain.Cafe;
import lee.joohan.whattoeattelegrambot.domain.User;
import lee.joohan.whattoeattelegrambot.exception.AlreadyExistCafeException;
import lee.joohan.whattoeattelegrambot.repository.CafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

/**
 * Created by Joohan Lee on 2020/04/27
 */

@Service
@RequiredArgsConstructor

public class CafeService {
  private final CafeRepository cafeRepository;

  public Mono<Cafe> register(Mono<Tuple2<User, String>> userCafeNameTuple) {
    return cafeRepository.findByName(userCafeNameTuple.map(Tuple2::getT2))
        .<Cafe>flatMap(cafe -> Mono.error(AlreadyExistCafeException.fromName(cafe.getName())))
        .switchIfEmpty(
            userCafeNameTuple.flatMap(it ->
                cafeRepository.save(
                    Cafe.builder()
                        .name(it.getT2())
                        .creatorId(it.getT1().getId())
                        .build()
                )
            )
        );
  }
}
