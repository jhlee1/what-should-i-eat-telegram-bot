package lee.joohan.whattoeattelegrambot.repository;

import lee.joohan.whattoeattelegrambot.domain.Cafe;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Joohan Lee on 2020/03/12
 */
public interface CafeRepository extends MongoRepository<Cafe, ObjectId> {

}
