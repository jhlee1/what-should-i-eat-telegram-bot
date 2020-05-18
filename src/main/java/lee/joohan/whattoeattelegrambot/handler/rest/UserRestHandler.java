package lee.joohan.whattoeattelegrambot.handler.rest;

import lee.joohan.whattoeattelegrambot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Created by Joohan Lee on 2020/05/18
 */


@RequiredArgsConstructor
@Component
public class UserRestHandler {
    UserService userService;
    //TODO: 유저관리는 나중에 추가

}
