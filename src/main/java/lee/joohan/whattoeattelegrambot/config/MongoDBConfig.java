package lee.joohan.whattoeattelegrambot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.ReactiveMongoTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;

/**
 * Created by Joohan Lee on 2020/04/27
 */

@Configuration
public class MongoDBConfig {
  @Bean
  TransactionalOperator transactionalOperator(ReactiveTransactionManager reactiveTransactionManager) {
    return TransactionalOperator.create(reactiveTransactionManager);
  }

  @Bean
  ReactiveTransactionManager transactionalManager(ReactiveMongoDatabaseFactory reactiveMongoDatabaseFactory) {
    return new ReactiveMongoTransactionManager(reactiveMongoDatabaseFactory);
  }
}
