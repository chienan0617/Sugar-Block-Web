package dev.cas.game.block2.standard.script;

import dev.cas.game.block2.standard.object.Tile;

public final class GameController {
  public static final int REMOVE_LINE_GAINED_SCORE = 10;
  public static final int RESET_COMBO_ROUND_TIMES = 3;

  public static long currentScore = 0;
  public static int lineRemoved = 0;
  public static int shapePlaced = 0;
  public static int currentCombo = 0;
  public static int noComboOccurredRound = 0;

  public static void onRemoveLine(int num) {
    if (num > 0 && num <= (Tile.ROWS + Tile.COLS)) {
      currentScore += (num * REMOVE_LINE_GAINED_SCORE) * currentCombo;
    }
  }

  public static void afterShapePlaced(boolean removedLine, int num) {
    if (!removedLine) {
      noComboOccurredRound += 1;
    } else {
      onRemoveLine(num);
    }

    if (noComboOccurredRound >= RESET_COMBO_ROUND_TIMES) {
      currentCombo = 0;
    }
  }
}
