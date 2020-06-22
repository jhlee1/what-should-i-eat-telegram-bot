package lee.joohan.whattoeattelegrambot.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LadderGameService {
  private final Random random = new Random();

  public String play(String[] players, String[] reward) {
    final int width = players.length * 2 - 1 ;
    final int height = players.length * 2;
    boolean[][] ladders = new boolean[width][height];

    ladders = generateLadder(ladders, width, height);

    Map<String, String> result = traceResult(ladders, width, height, players, reward);

    return printLadder(ladders, width, height, players, reward, result);
  }

  private String printLadder(boolean[][] ladders, int width, int height, String[] players, String[] reward, Map<String, String> result) {
    StringBuilder stringBuilder = new StringBuilder();

    for (int i = 0; i < players.length; i++) {
      stringBuilder.append(players[i]);
      stringBuilder.append("  ");
    }

    stringBuilder.append("\n");

    for (int h = 0; h < height; h++) {
      for (int w = 0; w < width - 1; w++) {
        stringBuilder.append('|');
        w++;
        if (ladders[w][h]) {
          stringBuilder.append("--");
        } else {
          stringBuilder.append("  ");
        }

      }
      stringBuilder.append("|\n");
    }

    for (int i = 0; i < players.length; i++) {
      stringBuilder.append(reward[i]);
      stringBuilder.append("  ");
    }

    stringBuilder.append("\n");

    for(Entry<String, String> entry : result.entrySet()) {
      stringBuilder.append(entry.getKey());
      stringBuilder.append("=");
      stringBuilder.append(entry.getValue());
      stringBuilder.append(" | ");

    }

    return stringBuilder.toString();
  }

  private boolean[][] generateLadder(boolean[][] ladders, int width, int height) {
    for (int h = 0; h < height; h++) {
      for (int w = 1; w < width; w += 2) {
        if (random.nextBoolean()) {
          ladders[w][h] = true;
          w += 2;
        }
      }
    }

    return ladders;
  }

  private Map<String, String> traceResult(boolean[][] ladders, int width, int height, String[] players, String[] reward) {
    Map<String, String> result = new HashMap<>();

    for (int i = 0; i < width; i += 2) {
      int w = i;
      int h = 0;

      while(h < height) {
        if (w < width - 1 && ladders[w + 1][h]) {
          w += 2;
        } else if (w > 1 && ladders[w - 1][h]) {
          w -= 2;
        }
        h++;
      }

      result.put(players[i / 2], reward[w / 2]);
    }

    return result;
  }
}
