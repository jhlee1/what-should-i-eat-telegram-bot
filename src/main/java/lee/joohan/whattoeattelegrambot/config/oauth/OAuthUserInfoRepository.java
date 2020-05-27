package lee.joohan.whattoeattelegrambot.config.oauth;

import lee.joohan.whattoeattelegrambot.domain.OAuthUserInfo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

public interface OAuthUserInfoRepository extends ReactiveMongoRepository<OAuthUserInfo, ObjectId> {
  Mono<OAuthUserInfo> findByEmail(String name);
}
