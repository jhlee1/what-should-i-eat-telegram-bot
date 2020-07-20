package lee.joohan.whattoeattelegrambot.domain.delivery;

import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by Joohan Lee on 2020/07/17
 */

@NoArgsConstructor
@Getter
public class DeliveryUserInfo {
  private long telegramId;
  private String name;

  private Map<String, Integer> menus;

  @Builder
  public DeliveryUserInfo(long telegramId, String name) {
    this.name = name;
    this.telegramId = telegramId;
    this.menus = new HashMap<>();
  }

  public void addMenu(String name, int num) {
    menus.computeIfPresent(name, (key, val) -> val += num);
    menus.putIfAbsent(name, num);
  }
}
