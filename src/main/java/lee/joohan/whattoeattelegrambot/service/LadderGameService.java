package lee.joohan.whattoeattelegrambot.service;

import java.util.List;
import lee.joohan.whattoeattelegrambot.domain.Ladder;
import lee.joohan.whattoeattelegrambot.repository.LadderRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class LadderGameService {
  private final LadderRepository ladderRepository;

  public Mono<Ladder> play(ObjectId ladderId) {
    return ladderRepository.findById(ladderId)
        .flatMap(ladder -> {
          ladder.generateLadder();
          ladder.traceResult();

          return ladderRepository.save(ladder);
        });
  }

  public Mono<Ladder> create(List<String> players, List<String> reward) {
    return ladderRepository.save(new Ladder(players, reward));
  }
}
