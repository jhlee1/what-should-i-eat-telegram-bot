package lee.joohan.whattoeattelegrambot.repository;

import lee.joohan.whattoeattelegrambot.domain.delivery.DeliveryRestaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class DeliveryRestaurantRepositoryImpl implements DeliveryRestaurantRepositoryCustom {
  private final ReactiveMongoTemplate mongoTemplate;

  @Override
  public Flux<DeliveryRestaurant> randomSample(int num) {
    Aggregation aggregation = Aggregation.newAggregation(Aggregation.sample(num));

    return mongoTemplate.aggregate(aggregation, "delivery_restaurant", DeliveryRestaurant.class);
  }
}
