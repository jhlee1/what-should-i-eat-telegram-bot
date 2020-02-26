package lee.joohan.whattoeattelegrambot.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by Joohan Lee on 2020/02/16
 */


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BotCommand {
  public static final String ADD_MENU = "/메뉴추가";
  public static final String DELETE_MENU = "/메뉴삭제";
  public static final String ADD_RESTAURANT = "/맛집추가";
  public static final String EDIT_NAME_RESTAURANT = "/맛집이름변경";
  public static final String DELETE_RESTAURANT = "/맛집삭제";
  public static final String LIST_RESTAURANT = "/맛집리스트";
  public static final String LIST_MENU = "/메뉴";
  public static final String RANDOM_PICK = "/뭐먹";
  public static final String LIST_COMMANDS = "/명령어";
}
