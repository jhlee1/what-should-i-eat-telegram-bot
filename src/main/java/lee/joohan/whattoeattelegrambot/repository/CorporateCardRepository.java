package lee.joohan.whattoeattelegrambot.repository;

import lee.joohan.whattoeattelegrambot.domain.CorporateCard;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/04/07
 */
public interface CorporateCardRepository extends ReactiveMongoRepository<CorporateCard, ObjectId>, CorporateCardRepositoryCustom {
  Mono<CorporateCard> findByCardNum(Mono<Integer> cardNum);
  Flux<CorporateCard> findByCurrentUserId(ObjectId currentUserId);

}
