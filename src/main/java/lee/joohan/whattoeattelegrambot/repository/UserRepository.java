package lee.joohan.whattoeattelegrambot.repository;

import java.util.List;
import java.util.Optional;
import lee.joohan.whattoeattelegrambot.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Joohan Lee on 2020/02/15
 */
public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByTelegramId(long telegramId);
  List<User> findByLastNameAndFirstName(String lastName, String firstName);
}
