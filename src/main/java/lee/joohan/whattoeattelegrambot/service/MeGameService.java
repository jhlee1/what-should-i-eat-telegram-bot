package lee.joohan.whattoeattelegrambot.service;

import java.util.List;
import lee.joohan.whattoeattelegrambot.domain.MeGame;
import lee.joohan.whattoeattelegrambot.domain.MeGameStatus;
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
    return meGameRepository.findByChatRoomIdAndMeGameStatus(chatRoomId, MeGameStatus.STARTED)
        .flatMap(meGame -> {
          if (meGame.getPlayers().containsKey(player.getTelegramId())) {
            return Mono.error(new AssertionError("두번 등록 금지!"));
          }

          meGame.getPlayers().put(player.getTelegramId(), player);

          return meGameRepository.save(meGame);
        });
  }

  @Transactional
  public Mono<List<MeGameUser>> end(long chatRoomId) {
    return meGameRepository.findByChatRoomIdAndMeGameStatus(chatRoomId, MeGameStatus.STARTED)
        .map(meGame -> {
          List<MeGameUser> winners = meGame.end();

          meGameRepository.save(meGame);

          return winners;
        });
  }
}
