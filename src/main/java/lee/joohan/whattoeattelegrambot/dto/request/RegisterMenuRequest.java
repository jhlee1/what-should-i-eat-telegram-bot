package lee.joohan.whattoeattelegrambot.dto.request;

import lombok.Getter;
import org.bson.types.ObjectId;

/**
 * Created by Joohan Lee on 2020/05/13
 */

@Getter
public class RegisterMenuRequest {
  private String name;
  private int price;
  private ObjectId creatorId;


}
