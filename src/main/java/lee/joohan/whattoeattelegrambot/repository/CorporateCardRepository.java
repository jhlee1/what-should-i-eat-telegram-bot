package lee.joohan.whattoeattelegrambot.repository;

import java.util.List;
import lee.joohan.whattoeattelegrambot.domain.CorporateCard;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Joohan Lee on 2020/04/07
 */
public interface CorporateCardRepository extends MongoRepository<CorporateCard, ObjectId>, CorporateCardRepositoryCustom {
  CorporateCard findByCardNum(int cardNum);
  List<CorporateCard> findByCurrentUserId(ObjectId currentUserId);

}
