package lee.joohan.whattoeattelegrambot.dto.response.restaurant;

import java.util.List;
import java.util.stream.Collectors;
import lee.joohan.whattoeattelegrambot.domain.Menu;
import lee.joohan.whattoeattelegrambot.domain.Restaurant;
import lombok.Getter;

/**
 * Created by Joohan Lee on 2020/06/05
 */

@Getter
public class RestaurantListResponseDTO {
  private String restaurantId;
  private String name;
  private String address;
  private List<MenuDTO> menus;

  public RestaurantListResponseDTO(Restaurant restaurant) {
    restaurantId = restaurant.getId().toHexString();
    name = restaurant.getName();
    address = restaurant.getAddress();
    menus = restaurant.getMenus().stream()
        .map(MenuDTO::new)
        .collect(Collectors.toList());
  }

  @Getter
  public static class MenuDTO {
    private String name;
    private int price;

    public MenuDTO(Menu menu) {
      name = menu.getName();
      price = menu.getPrice();
    }
  }
}
