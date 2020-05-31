package lee.joohan.whattoeattelegrambot.repository;


import static org.springframework.data.mongodb.core.aggregation.Aggregation.lookup;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

import lee.joohan.whattoeattelegrambot.domain.dao.CorporateCardStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import reactor.core.publisher.Flux;

/**
 * Created by Joohan Lee on 2020/04/08
 */

@RequiredArgsConstructor
public class CorporateCardRepositoryImpl implements CorporateCardRepositoryCustom {
  private final ReactiveMongoTemplate mongoTemplate;

  @Override
  public Flux<CorporateCardStatus> findCardStatuses() {
    LookupOperation lookupOperation = lookup("user", "currentUserId", "_id", "userInfo");

    Aggregation aggregation = newAggregation(lookupOperation);

    return mongoTemplate.aggregate(aggregation, "corporate_card", CorporateCardStatus.class);
  }
}
