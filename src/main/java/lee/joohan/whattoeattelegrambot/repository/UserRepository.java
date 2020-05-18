package lee.joohan.whattoeattelegrambot.repository;

import lee.joohan.whattoeattelegrambot.domain.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/02/15
 */
public interface UserRepository extends ReactiveMongoRepository<User, ObjectId> {
  Mono<User> findByTelegramId(Mono<Long> telegramId);
  Mono<User> findByUsername(Mono<String> username);
}
