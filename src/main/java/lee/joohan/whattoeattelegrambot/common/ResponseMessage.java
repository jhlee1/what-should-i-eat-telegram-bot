package lee.joohan.whattoeattelegrambot.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Created by Joohan Lee on 2020/02/15
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseMessage {
  public static final String REGISTER_RESTAURANT_RESPONSE = "식당 등록 완료";
  public static final String DELETE_RESTAURANT_RESPONSE = "식당 삭제 완료";
  public static final String CHANGE_RESTAURANT_NAME_RESPONSE = "식당 이름 변경 완료";
  public static final String EAT = "먹어!";
  public static final String DO_NOT_EAT = "먹지마!";

  public static final String REGISTER_CAFE_RESPONSE = "카페 등록 완료";
  public static final String DELETE_CAFE_RESPONSE = "카페 삭제 완료";

  public static final String USE_CORPORATE_CARD = "카드 사용 등록 완료";
  public static final String RETURN_CORPORATE_CARD = "카드 반납 완료";


  public static final String REGISTER_RESTAURANT_ARGS_ERROR_RESPONSE = "식당등록 실패. 입력 형식을 확인하세요. (ex. /맛집추가 이름)";
  public static final String DELETE_RESTAURANT_ARGS_ERROR_RESPONSE = "식당삭제 실패. 입력 형식을 확인하세요. (ex. /맛집삭제 이름)";
  public static final String CHANGE_RESTAURANT_NAME_ARGS_ERROR_RESPONSE = "식당 이름 변경 실패. 입력 형식을 확인하세요. (ex. /맛집이름변경 현재_이름 변경할_이름)";
  public static final String REGISTER_MENU_ARGS_ERROR_RESPONSE = "메뉴등록 실패. 입력 형식을 확인하세요. (ex. /메뉴추가 맛집이름 메뉴 (가격))";
  public static final String EAT_OR_NOT_ARGS_ERROR_RESPONSE = "메뉴를 입력하세요. (ex. /먹을까 탕수육)";
  public static final String RANDOM_PICK_ARGS_ERROR_RESPONSE = "뭐먹 잘못된 입력. (ex. /뭐먹 || /뭐먹 2)";


  public static final String REGISTER_CAFE_ARGS_ERROR_RESPONSE = "카페등록 실패. 입력 형식을 확인하세요. (ex. /카페추가 이름)";

  public static final String USE_CORPORATE_CARD_ARGS_ERROR_RESPONSE = "카드 사용 등록 실패. 입력 형식을 확인하세요. (ex. /카드사용 번호)";
  public static final String PUT_BACK_CORPORATE_CARD_ARGS_ERROR_RESPONSE = "카드 반납 실패. 입력 형식을 확인하세요. (ex. /카드반납 번호)";
  public static final String PUT_BACK_NOT_OWNED_CORPORATE_CARD_ERROR_RESPONSE = "카드 반납 실패. 입력 형식을 확인하세요. (ex. /카드반납 번호)";

  public static final String NO_COMMAND_FOUND_ERROR_RESPONSE = "명령어가 없습니다.";
}

