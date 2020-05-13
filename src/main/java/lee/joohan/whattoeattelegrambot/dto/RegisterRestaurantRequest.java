package lee.joohan.whattoeattelegrambot.dto;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * Created by Joohan Lee on 2020/05/13
 */

@Getter
public class RegisterRestaurantRequest {
  @NotBlank
  private String name;

  @NotBlank
  private String address;

  @Valid
  private List<RegisterMenuRequest> menus;
}
