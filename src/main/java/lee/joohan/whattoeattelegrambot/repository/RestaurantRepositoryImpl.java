package lee.joohan.whattoeattelegrambot.repository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sample;

import lee.joohan.whattoeattelegrambot.domain.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.SampleOperation;
import reactor.core.publisher.Flux;

/**
 * Created by Joohan Lee on 2020/06/08
 */

@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepositoryCustom {
  private final ReactiveMongoTemplate mongoTemplate;

  @Override
  public Flux<Restaurant> getRandomSample(int num) {
    SampleOperation sampleOperation = sample(num);

    Aggregation aggregation = newAggregation(sampleOperation);

    return mongoTemplate.aggregate(aggregation, "restaurant", Restaurant.class);
  }
}
