package lee.joohan.whattoeattelegrambot.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Joohan Lee on 2020/07/24
 */

@Getter
@NoArgsConstructor
@Document("ladder")
public class Ladder {
  @Id
  private ObjectId id;

  private static final Random random = new Random();
  private int width;
  private int height;
  private boolean[][] grid;
  private List<String> players;
  private List<String> rewards;
  private Map<String, String> result;

  public Ladder(List<String> players, List<String> rewards) {
    this.players = players;
    this.rewards = rewards;
    result = new HashMap<>();

    width = players.size() * 2 - 1 ;
    height = players.size() * 2;
    grid = new boolean[width][height];
  }

  public void generateLadder() {
    for (int h = 0; h < height; h++) {
      for (int w = 1; w < width; w += 2) {
        if (random.nextBoolean()) {
          grid[w][h] = true;
          w += 2;
        }
      }
    }
  }

  public void traceResult() {
    for (int i = 0; i < width; i += 2) {
      int w = i;
      int h = 0;

      while(h < height) {
        if (w < width - 1 && grid[w + 1][h]) {
          w += 2;
        } else if (w > 1 && grid[w - 1][h]) {
          w -= 2;
        }
        h++;
      }

      result.put(players.get(i / 2), rewards.get(w / 2));
    }
  }

  public String printGrid() {
    StringBuilder stringBuilder = new StringBuilder();

    for (int i = 0; i < players.size(); i++) {
      stringBuilder.append(players.get(i));
      stringBuilder.append("  ");
    }

    stringBuilder.append("\n");

    for (int h = 0; h < height; h++) {
      for (int w = 0; w < width - 1; w++) {
        stringBuilder.append('|');
        w++;
        if (grid[w][h]) {
          stringBuilder.append("--");
        } else {
          stringBuilder.append("  ");
        }

      }
      stringBuilder.append("|\n");
    }

    for (int i = 0; i < players.size(); i++) {
      stringBuilder.append(rewards.get(i));
      stringBuilder.append("  ");
    }

    stringBuilder.append("\n");

    stringBuilder.append(showResult());

    return stringBuilder.toString();
  }

  public String showResult() {
    return result.entrySet().stream()
        .collect(Collectors.groupingBy(Entry::getValue, Collectors.mapping(Entry::getKey, Collectors.toList())))
        .entrySet().stream()
        .map(entry -> {
          StringBuilder sb = new StringBuilder(entry.getKey());

          sb.append(" = ");
          sb.append(entry.getValue().stream()
              .collect(Collectors.joining(",")));

          return sb.toString();
        })
        .collect(Collectors.joining("\n"));
  }
}
