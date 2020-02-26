package lee.joohan.whattoeattelegrambot.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Created by Joohan Lee on 2020/02/15
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseMessage {
  public static final String REGISTER_MENU_RESPONSE = "메뉴 등록 완료";
  public static final String REGISTER_USER_RESPONSE = "유저 등록 완료";
  public static final String REGISTER_RESTAURANT_RESPONSE = "식당 등록 완료";
  public static final String DELETE_RESTAURANT_RESPONSE = "식당 삭제 완료";
  public static final String CHANGE_RESTAURANT_NAME_RESPONSE = "식당 이름 변경 완료";

  public static final String REGISTER_USER_ARGS_ERROR_RESPONSE = "유저등록 실패. 입력 형식을 확인하세요. (ex. /유저추가 유저명)";
  public static final String DELETE_USER_ARGS_ERROR_RESPONSE = "유저삭제 실패. 입력 형식을 확인하세요. (ex. /유저삭제 유저명)";
  public static final String REGISTER_RESTAURANT_ARGS_ERROR_RESPONSE = "식당등록 실패. 입력 형식을 확인하세요. (ex. /맛집추가 이름)";
  public static final String DELETE_RESTAURANT_ARGS_ERROR_RESPONSE = "식당삭제 실패. 입력 형식을 확인하세요. (ex. /맛집삭제 이름)";
  public static final String CHANGE_RESTAURANT_NAME_ARGS_ERROR_RESPONSE = "식당 이름 변경 실패. 입력 형식을 확인하세요. (ex. /맛집이름변경 현재_이름 변경할_이름)";
  public static final String REGISTER_MENU_ARGS_ERROR_RESPONSE = "메뉴등록 실패. 입력 형식을 확인하세요. (ex. /메뉴추가 맛집이름 메뉴 (가격))";
  public static final String NO_COMMAND_FOUND_ERROR_RESPONSE = "명령어가 없습니다.";
}

