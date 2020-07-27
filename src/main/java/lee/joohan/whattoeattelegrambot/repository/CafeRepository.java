package lee.joohan.whattoeattelegrambot.repository;

import lee.joohan.whattoeattelegrambot.domain.Cafe;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/03/12
 */
public interface CafeRepository extends ReactiveMongoRepository<Cafe, ObjectId>, CafeRepositoryCustom {

  Mono<Cafe> findByName(String name);

}
