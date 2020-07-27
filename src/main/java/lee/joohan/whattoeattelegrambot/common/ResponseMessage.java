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

  public static final String VERIFY_ACCOUNT = "계정 인증 완료";

  public static final String REGISTER_RESTAURANT_ARGS_ERROR_RESPONSE = "식당등록 실패. 입력 형식을 확인하세요. (ex. /맛집추가 이름)";
  public static final String DELETE_RESTAURANT_ARGS_ERROR_RESPONSE = "식당삭제 실패. 입력 형식을 확인하세요. (ex. /맛집삭제 이름)";
  public static final String CHANGE_RESTAURANT_NAME_ARGS_ERROR_RESPONSE = "식당 이름 변경 실패. 입력 형식을 확인하세요. (ex. /맛집이름변경 현재_이름 변경할_이름)";
  public static final String REGISTER_MENU_ARGS_ERROR_RESPONSE = "메뉴등록 실패. 입력 형식을 확인하세요. (ex. /메뉴추가 맛집이름 메뉴 (가격))";
  public static final String EAT_OR_NOT_ARGS_ERROR_RESPONSE = "메뉴를 입력하세요. (ex. /먹을까 탕수육)";
  public static final String RANDOM_PICK_ARGS_ERROR_RESPONSE = "뭐먹 잘못된 입력. (ex. /뭐먹 || /뭐먹 2)";

  public static final String REGISTER_CAFE_ARGS_ERROR_RESPONSE = "카페등록 실패. 입력 형식을 확인하세요. (ex. /카페추가 이름)";

  public static final String USE_CORPORATE_CARD_ARGS_ERROR_RESPONSE = "카드 사용 등록 실패. 입력 형식을 확인하세요. (ex. /법카사용 번호)";
  public static final String CORPORATE_CARD_ALREADY_IN_USE_ERROR_RESPONSE = "이미 사용중인 카드입니다.";
  public static final String CORPORATE_CARD_ALREADY_IN_RETURNED_ERROR_RESPONSE = "대여 중인 카드가 아닙니다.";
  public static final String PUT_BACK_CORPORATE_CARD_ARGS_ERROR_RESPONSE = "카드 반납 실패. 입력 형식을 확인하세요. (ex. /법카반납 번호)";
  public static final String PUT_BACK_NOT_OWNED_CORPORATE_CARD_ERROR_RESPONSE = "카드 반납 실패. 빌려간 카드가 없습니다.";

  public static final String VERIFY_ACCOUNT_ARGS_ERROR_RESPONSE = "계정 인증 실패. 입력 형식을 확인하세요. (ex. /인증 joolee@ogqcorp.com)";
  public static final String VERIFY_ACCOUNT_NOT_FOUND_USER_ERROR_RESPONSE = "계정 인증 실패. 찾을 수 없는 계정입니다. (ex. /인증 joolee@ogqcorp.com)";
  public static final String ALREADY_VERIFIED_EMAIL_ERROR_RESPONSE = "계정 인증 실패. 이미 인증된 이메일입니다.";
  public static final String ALREADY_VERIFIED_TELEGRAM_ID_ERROR_RESPONSE = "계정 인증 실패. 해당 텔레그램 계정은 이미 인증되었습니다.";

  public static final String START_LADDER_GAME_ARGS_ERROR_RESPONSE = "사다리 잘못된 입력. (ex. /사다리 1번 2번 3번 : 상품1 상품2 상품3)";
  public static final String CREATE_LADDER_GAME_GROUP_ARGS_ERROR_RESPONSE = "그룹추가 실패. (ex. /사다리그룹추가 그룹명)";
  public static final String ALREADY_EXISTING_LADDER_GAME_GROUP_ERROR_RESPONSE = "그룹추가 실패. 이미 존재하는 그룹명";
  public static final String DELETE_LADDER_GAME_GROUP_ARGS_ERROR_RESPONSE = "그룹삭제 실패. (ex. /사다리그룹삭제 그룹명)";
  public static final String ADD_LADDER_GAME_GROUP_MEMBER_ARGS_ERROR_RESPONSE = "그룹멤버 추가 실패. (ex. /멤버추가 그룹명 멤버이름)";
  public static final String REMOVE_LADDER_GAME_GROUP_MEMBER_ARGS_ERROR_RESPONSE = "그룹멤버 삭제 실패. (ex. /멤버삭제 그룹명 멤버이름)";

  public static final String DELIVERY_NOT_FOUND_ERROR_RESPONSE = "진행중인 배달아이템이 없습니다. 새로 시작하세요. (ex. /배달)";
  public static final String DELIVERY_ADD_MENU_ARGS_ERROR_RESPONSE = "잘못된 배달메뉴 추가. (ex. /배달추가 김밥 1)";
  public static final String ONLY_OWNER_CAN_END_DELIVERY_ERROR_RESPONSE = "배달을 시작한 사람만 마감가능.";

  public static final String NO_COMMAND_FOUND_ERROR_RESPONSE = "명령어가 없습니다.";
}

