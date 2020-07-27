package lee.joohan.whattoeattelegrambot.service;

import lee.joohan.whattoeattelegrambot.domain.LadderGameGroup;
import lee.joohan.whattoeattelegrambot.exception.NotFoundGameGroupException;
import lee.joohan.whattoeattelegrambot.repository.LadderGameGroupRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/07/24
 */

@Getter
@RequiredArgsConstructor
@Service
public class LadderGameGroupService {
  private final LadderGameGroupRepository ladderGameGroupRepository;

  public Mono<LadderGameGroup> create(String name) {
    return ladderGameGroupRepository.save(new LadderGameGroup(name));
  }

  public Mono<Void> delete(String groupName) {
    return ladderGameGroupRepository.findByName(groupName)
        .switchIfEmpty(Mono.error(() -> new NotFoundGameGroupException(groupName)))
        .flatMap(ladderGameGroupRepository::delete);
  }

  public Mono<LadderGameGroup> get(String groupName) {
    return ladderGameGroupRepository.findByName(groupName);
  }

  public Flux<LadderGameGroup> getAll() {
    return ladderGameGroupRepository.findAll();
  }

  public Mono<LadderGameGroup> addMember(String groupName, String username) {
    return ladderGameGroupRepository.findByName(groupName)
        .switchIfEmpty(Mono.error(() -> new NotFoundGameGroupException(groupName)))
        .flatMap(it -> {
          it.addUser(username);
          return ladderGameGroupRepository.save(it);
        });
  }

  public Mono<LadderGameGroup> removeMember(String groupName, String username) {
    return ladderGameGroupRepository.findByName(groupName)
        .switchIfEmpty(Mono.error(() -> new NotFoundGameGroupException(groupName)))
        .flatMap(it -> {
          it.removeUser(username);
          return ladderGameGroupRepository.save(it);
        });
  }
}
