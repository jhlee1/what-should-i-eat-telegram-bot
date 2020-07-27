package lee.joohan.whattoeattelegrambot.repository;

import lee.joohan.whattoeattelegrambot.domain.Ladder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Joohan Lee on 2020/07/24
 */

@Repository
public interface LadderRepository extends ReactiveMongoRepository<Ladder, ObjectId> {
}
