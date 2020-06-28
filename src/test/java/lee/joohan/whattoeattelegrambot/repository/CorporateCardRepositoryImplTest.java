package lee.joohan.whattoeattelegrambot.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by Joohan Lee on 2020/04/08
 */

@SpringBootTest
class CorporateCardRepositoryImplTest {
  @Autowired
  CorporateCardRepository corporateCardRepository;

  @Test
  void findCardStatuses() {
    System.out.println(corporateCardRepository.findCardStatuses());
  }
}