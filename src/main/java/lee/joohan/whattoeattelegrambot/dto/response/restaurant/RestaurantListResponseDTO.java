package lee.joohan.whattoeattelegrambot.dto.response.restaurant;

import lee.joohan.whattoeattelegrambot.domain.Restaurant;
import lombok.Getter;

/**
 * Created by Joohan Lee on 2020/06/05
 */

@Getter
public class RestaurantListResponseDTO {
  private String restaurantId;
  private String name;

  public RestaurantListResponseDTO(Restaurant restaurant) {
    restaurantId = restaurant.getId().toHexString();
    name = restaurant.getName();
  }
}
