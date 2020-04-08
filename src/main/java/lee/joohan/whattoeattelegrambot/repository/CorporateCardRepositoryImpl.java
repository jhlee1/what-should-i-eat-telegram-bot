package lee.joohan.whattoeattelegrambot.repository;


import static org.springframework.data.mongodb.core.aggregation.Aggregation.lookup;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

import java.util.List;
import lee.joohan.whattoeattelegrambot.domain.dao.CorporateCardStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;

/**
 * Created by Joohan Lee on 2020/04/08
 */

@RequiredArgsConstructor
public class CorporateCardRepositoryImpl implements CorporateCardRepositoryCustom {
  private final MongoTemplate mongoTemplate;

  @Override
  public List<CorporateCardStatus> findCardStatuses() {
    LookupOperation lookupOperation = lookup("user", "currentUserId", "_id", "userInfo");

    Aggregation aggregation = newAggregation(lookupOperation);

    return mongoTemplate.aggregate(aggregation, "corporate_card", CorporateCardStatus.class).getMappedResults();
  }
}
