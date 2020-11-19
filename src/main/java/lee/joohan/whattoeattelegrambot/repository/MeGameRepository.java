package lee.joohan.whattoeattelegrambot.repository;

import lee.joohan.whattoeattelegrambot.domain.MeGame;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/06/18
 */

@Repository
public interface MeGameRepository extends ReactiveMongoRepository<MeGame, ObjectId> {
  Mono<MeGame> findByChatRoomId(long chatRoomId);

}
