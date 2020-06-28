package lee.joohan.whattoeattelegrambot.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lee.joohan.whattoeattelegrambot.domain.Restaurant;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by Joohan Lee on 2020/06/08
 */

@SpringBootTest
class RestaurantServiceTest {
  @Autowired
  RestaurantService restaurantService;

  @Test
  @DisplayName("뭐먹 중복 검사. 오래걸리는 테스트이기 때문에 임시로 Disabled시킴")
  @Disabled
  void randomSampleDuplicationTest() {
    int totalRestaurantCount = restaurantService.getAll()
        .collectList()
        .map(List::size)
        .block();

    for (int i = 1; i <= totalRestaurantCount; i++) {
      List<String> rawRestaurants = restaurantService.randomSample(i)
          .map(Restaurant::getName)
          .collectList()
          .block();

      Set<String> duplicationRemovedRestaurantNames = rawRestaurants.stream()
          .collect(Collectors.toSet());

      assertThat(rawRestaurants.size(), equalTo(duplicationRemovedRestaurantNames.size()));
      assertThat(rawRestaurants, containsInAnyOrder(duplicationRemovedRestaurantNames.toArray()));
    }
  }
}
