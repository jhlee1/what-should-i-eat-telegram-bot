package lee.joohan.whattoeattelegrambot.repository;

import lee.joohan.whattoeattelegrambot.domain.telegram.TelegramMessage;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Joohan Lee on 2020/05/31
 */

@Repository
public interface TelegramMessageRepository extends ReactiveMongoRepository<TelegramMessage, ObjectId> {

}
