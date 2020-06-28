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
  public static final String ADD_RESTAURANT = "/맛집추가";
  public static final String EDIT_NAME_RESTAURANT = "/맛집이름변경";
  public static final String DELETE_RESTAURANT = "/맛집삭제";
  public static final String LIST_RESTAURANT = "/맛집리스트";
  public static final String LIST_MENU = "/메뉴";
  public static final String RANDOM_PICK = "/뭐먹";
  public static final String LIST_COMMANDS = "/명령어";
  public static final String NOT_EAT = "/안먹";
  public static final String EAT_OR_NOT = "/먹을까";

  public static final String LIST_CAFE = "/카페리스트";
  public static final String ADD_CAFE = "/카페추가";
  public static final String DELETE_CAFE = "/카페삭제";
  public static final String PICK_RANDOM_CAFE = "/뭐마";

  public static final String USE_CORPORATE_CREDIT_CARD = "/법카사용";
  public static final String RETURN_CORPORATE_CREDIT_CARD = "/법카반납";
  public static final String LIST_CORPORATE_CREDIT_CARD = "/법카현황";

  public static final String START_ME_GAME = "/저요시작";
  public static final String ME = "/저요";
  public static final String END_ME_GAME = "/저요끝";

  public static final String VERIFY_ACCOUNT = "/인증";


  public static final String LADDER_GAME = "/사다리게임";
  public static final String EMPTY = "";
}
