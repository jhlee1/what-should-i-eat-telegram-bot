package lee.joohan.whattoeattelegrambot.repository;

import lee.joohan.whattoeattelegrambot.domain.LadderGameGroup;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/07/25
 */

@Repository
public interface LadderGameGroupRepository extends ReactiveMongoRepository<LadderGameGroup, ObjectId> {

  Mono<LadderGameGroup> findByName(String name);
}
