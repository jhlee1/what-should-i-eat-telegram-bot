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
  public static final String DELETE_RESTAURANT = "/맛집삭제";
  public static final String LIST_RESTAURANT = "/맛집리스트";
  public static final String RANDOM_PICK = "/뭐먹";
  public static final String LIST_COMMANDS = "/명령어";
  public static final String NOT_EAT = "/안먹";
  public static final String EAT_OR_NOT = "/먹을까";

  public static final String LIST_CAFE = "/카페리스트";
  public static final String ADD_CAFE = "/카페추가";
  public static final String DELETE_CAFE = "/카페삭제";
  public static final String PICK_RANDOM_CAFE = "/뭐마";

  public static final String USE_CORPORATE_CREDIT_CARD = "/법카사용";
  public static final String PUT_BACK_CORPORATE_CREDIT_CARD = "/법카반납";
  public static final String LIST_CORPORATE_CREDIT_CARD = "/법카현황";

  public static final String VERIFY_ACCOUNT = "/인증";

  public static final String PLAY_LADDER_GAME = "/사다리게임";

  public static final String CREATE_LADDER_GAME_USER_GROUP = "/유저그룹생성";
  public static final String DELETE_LADDER_GAME_USER_GROUP = "/유저그룹삭제";
  public static final String LIST_LADDER_GAME_USER_GROUP = "/유저그룹리스트";
  public static final String ADD_LADDER_GAME_USER_GROUP_MEMBER = "/인원추가";
  public static final String REMOVE_LADDER_GAME_USER_GROUP_MEMBER = "/인원삭제";
  public static final String SPLIT_LADDER_GAME_GROUP = "/그룹나누기";
  public static final String SHOW_GAME_GROUP_MEMBER = "/멤버보기";

  public static final String DELIVERY_START = "/배달";
  public static final String DELIVERY_ADD_MENU = "/배달추가";
  public static final String DELIVERY_END = "/배달마감";


  public static final String EMPTY = "";
}
