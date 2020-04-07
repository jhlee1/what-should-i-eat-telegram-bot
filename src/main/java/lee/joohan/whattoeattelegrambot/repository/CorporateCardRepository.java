package lee.joohan.whattoeattelegrambot.repository;

import lee.joohan.whattoeattelegrambot.domain.CorporateCard;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Joohan Lee on 2020/04/07
 */
public interface CorporateCardRepository extends MongoRepository<CorporateCard, ObjectId> {
  CorporateCard findByCardNum(int cardNum);
  CorporateCard findByCurrentUserId(ObjectId currentUserId);

}
