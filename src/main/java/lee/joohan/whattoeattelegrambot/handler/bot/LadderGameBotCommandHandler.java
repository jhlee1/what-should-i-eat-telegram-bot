package lee.joohan.whattoeattelegrambot.handler.bot;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lee.joohan.whattoeattelegrambot.common.ResponseMessage;
import lee.joohan.whattoeattelegrambot.domain.Ladder;
import lee.joohan.whattoeattelegrambot.domain.LadderGameGroup;
import lee.joohan.whattoeattelegrambot.domain.telegram.TelegramMessage;
import lee.joohan.whattoeattelegrambot.exception.AlreadyExistingGameGroupUserException;
import lee.joohan.whattoeattelegrambot.exception.NotFoundGameGroupException;
import lee.joohan.whattoeattelegrambot.service.LadderGameGroupService;
import lee.joohan.whattoeattelegrambot.service.LadderGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class LadderGameBotCommandHandler {
  private final LadderGameService ladderGameService;
  private final LadderGameGroupService ladderGameGroupService;

  public Mono<String> play(Mono<TelegramMessage> telegramMessageMono) {
    return telegramMessageMono.filter(message -> Pattern.matches("/\\S+(\\s*\\S+)+\\s*:\\s*(\\S+\\s*)+", message.getText()))
        .switchIfEmpty(Mono.error(new IllegalArgumentException()))
        .flatMap(telegramMessage -> {
          String[] args = telegramMessage.getText().split(":");
          String[] firstArg = args[0].strip().split(" ");
          String[] players = Arrays.copyOfRange(firstArg, 1, firstArg.length);
          String[] rewards = args[1].strip().split(" ");

          if (players.length != rewards.length) {
            return Mono.error(new IllegalArgumentException());
          }

          return ladderGameService.create(Arrays.asList(players), Arrays.asList(rewards))
              .map(Ladder::getId);
        })
        .flatMap(id -> ladderGameService.play(id))
        .map(Ladder::printGrid)
        .onErrorReturn(IllegalArgumentException.class, ResponseMessage.START_LADDER_GAME_ARGS_ERROR_RESPONSE);
  }

  public Mono<String> createGameGroup(TelegramMessage telegramMessage) {
    String message = telegramMessage.getText();

    if (!Pattern.matches("/\\S+ \\S+", message)) {
      return Mono.just(ResponseMessage.CREATE_LADDER_GAME_GROUP_ARGS_ERROR_RESPONSE);
    }

    String name = message.split(" ")[1];

    return ladderGameGroupService.get(name)
        .map(it -> ResponseMessage.ALREADY_EXISTING_LADDER_GAME_GROUP_ERROR_RESPONSE)
        .switchIfEmpty(
            ladderGameGroupService.create(name)
                .map(it -> "사다리 게임 그룹 생성완료")
        );
  }

  public Mono<String> deleteGameGroup(TelegramMessage telegramMessage) {
    String message = telegramMessage.getText();

    if (!Pattern.matches("/\\S+ \\S+", message)) {
      return Mono.just(ResponseMessage.DELETE_LADDER_GAME_GROUP_ARGS_ERROR_RESPONSE);
    }

    String name = message.split(" ")[1];

    return ladderGameGroupService.delete(name)
        .thenReturn("사다리 게임 그룹 삭제 완료")
        .onErrorReturn("사다리 게임 그룹 삭제 실패. 존재하지 않는 그룹명입니다.");
  }

  public Mono<String> listGameGroups() {
    return ladderGameGroupService.getAll()
        .map(LadderGameGroup::getName)
        .sort()
        .collect(Collectors.joining("\n"));
  }

  public Mono<String> listGameGroupMembers(TelegramMessage telegramMessage) {
    String message = telegramMessage.getText();

    if (!Pattern.matches("/\\S+ \\S+", message)) {
      return Mono.just(ResponseMessage.ADD_LADDER_GAME_GROUP_MEMBER_ARGS_ERROR_RESPONSE);
    }

    String groupName = message.split(" ")[1];

    return ladderGameGroupService.get(groupName)
        .map(it -> {
          String list = it.getUsers()
              .stream()
              .sorted()
              .collect(Collectors.joining("\n"));

          return new StringBuilder(list)
              .append("\n")
              .append("총: ")
              .append(it.getUsers().size())
              .append(" 명")
              .toString();
        })
        .onErrorReturn(NotFoundGameGroupException.class, "존재하지 않는 그룹명입니다.");
  }


  public Mono<String> addGameGroupMember(TelegramMessage telegramMessage) {
    String message = telegramMessage.getText();

    if (!Pattern.matches("/\\S+ \\S+ \\S+", message)) {
      return Mono.just(ResponseMessage.ADD_LADDER_GAME_GROUP_MEMBER_ARGS_ERROR_RESPONSE);
    }

    String groupName = message.split(" ")[1];
    String memberName = message.split(" ")[2];

    return ladderGameGroupService.addMember(groupName, memberName)
        .map(it -> it.getUsers().stream().collect(Collectors.joining("\n")))
        .onErrorReturn(NotFoundGameGroupException.class, "그룹멤버 추가 실패. 존재하지 않는 그룹명입니다.")
        .onErrorReturn(AlreadyExistingGameGroupUserException.class, "그룹멤버 추가 실패. 해당 유저가 이미 그룹에 존재합니다.");
  }

  public Mono<String> removeGameGroupMember(TelegramMessage telegramMessage) {
    String message = telegramMessage.getText();

    if (!Pattern.matches("/\\S+ \\S+ \\S+", message)) {
      return Mono.just(ResponseMessage.REMOVE_LADDER_GAME_GROUP_MEMBER_ARGS_ERROR_RESPONSE);
    }

    String groupName = message.split(" ")[1];
    String memberName = message.split(" ")[2];

    return ladderGameGroupService.removeMember(groupName, memberName)
        .map(it -> it.getUsers().stream().collect(Collectors.joining("\n")))
        .onErrorReturn(NotFoundGameGroupException.class, "그룹 멤버 삭제 실패. 존재하지 않는 그룹명입니다.")
        .onErrorReturn(NotFoundGameGroupException.class, "그룹 멤버 삭제 실패. 존재하지 않는 유저입니다.");
  }

  public Mono<String> splitGameGroupMember(TelegramMessage telegramMessage) {
    String message = telegramMessage.getText();

    if (!Pattern.matches("/\\S+ \\S+ \\d+", message)) {
      return Mono.just("그룹 나누기 실패. 잘못된 입력 (ex. /그룹나누기 마서본 4");
    }

    String groupName = message.split(" ")[1];
    int splitBy = Integer.parseInt(message.split(" ")[2]);

    return ladderGameGroupService.get(groupName)
        .map(LadderGameGroup::getUsers)
        .flatMap(users -> {
          final int loopCount = users.size();
          List<String> rewards = new ArrayList<>();

          for (int i = 0; i < loopCount; i += splitBy) {
            for (int j = 1; j + i <= loopCount && j <= splitBy; j++) {
              rewards.add(String.valueOf(j));
            }
          }

          return ladderGameService.create(users, rewards)
              .map(Ladder::getId)
              .flatMap(ladderGameService::play)
              .map(Ladder::printGrid);
        }).onErrorReturn(NotFoundGameGroupException.class, "사다리 그룹 나누기 실패. 존재하지 않는 그룹명입니다.");
  }
}
