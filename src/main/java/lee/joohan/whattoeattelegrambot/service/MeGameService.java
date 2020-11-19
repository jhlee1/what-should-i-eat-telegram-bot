package lee.joohan.whattoeattelegrambot.service;

import lee.joohan.whattoeattelegrambot.domain.MeGame;
import lee.joohan.whattoeattelegrambot.domain.MeGameUser;
import lee.joohan.whattoeattelegrambot.repository.MeGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/06/18
 */

@RequiredArgsConstructor
@Service
public class MeGameService {
  private final MeGameRepository meGameRepository;

  @Transactional
  public Mono<MeGame> create(long chatRoomId, MeGameUser owner) {
    MeGame meGame = MeGame.builder()
        .chatRoomId(chatRoomId)
        .owner(owner)
        .build();

    return meGameRepository.save(meGame);
  }

  @Transactional
  public Mono<MeGame> addPlayer(long chatRoomId, MeGameUser player) {
    return meGameRepository.findByChatRoomId(chatRoomId)
        .flatMap(meGame -> {
          if (meGame.getPlayers().get(player.getId()) != null) {
            return Mono.error(new AssertionError("두번 등록밑장빼기 금지!"));
          }

          meGame.getPlayers().put(player.getId(), player);

          return meGameRepository.save(meGame);
        });
  }

  @Transactional
  public Mono<MeGame> end(long chatRoomId) {
    return meGameRepository.findByChatRoomId(chatRoomId)
        .map(meGame -> {
            meGame.getPlayers()
                .values()
                .stream()
                .

        })

  }
}
