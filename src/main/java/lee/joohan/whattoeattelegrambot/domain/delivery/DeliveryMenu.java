package lee.joohan.whattoeattelegrambot.domain.delivery;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by Joohan Lee on 2020/07/17
 */

@NoArgsConstructor
@Getter
public class DeliveryMenu {
  private String name;
  private DeliveryUserInfo deliveryUserInfo;

  @Builder
  public DeliveryMenu(String name, DeliveryUserInfo deliveryUserInfo) {
    this.name = name;
    this.deliveryUserInfo = deliveryUserInfo;
  }
}
