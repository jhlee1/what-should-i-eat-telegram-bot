package lee.joohan.whattoeattelegrambot.repository;

import java.util.Optional;
import lee.joohan.whattoeattelegrambot.domain.Restaurant;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Joohan Lee on 2020/02/15
 */

@Repository
public interface RestaurantRepository extends MongoRepository<Restaurant, ObjectId> {
  Optional<Restaurant> findByName(String name);
}
