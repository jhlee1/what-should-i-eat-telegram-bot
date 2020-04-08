package lee.joohan.whattoeattelegrambot.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Joohan Lee on 2020/04/08
 */

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CorporateCardRepositoryImplTest {

  @Autowired
  CorporateCardRepository corporateCardRepository;
  @Test
  void findCardStatuses() {
    System.out.println(corporateCardRepository.findCardStatuses());
  }
}