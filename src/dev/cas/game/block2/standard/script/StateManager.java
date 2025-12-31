package dev.cas.game.block2.standard.script;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.cas.engine.animation.ShockAnimation;
import com.cas.engine.core.ObjectManager;

import dev.cas.game.block2.standard.format.BoardPosition;
import dev.cas.game.block2.standard.format.MatrixType;
import dev.cas.game.block2.standard.object.Shape;
import dev.cas.game.block2.standard.object.Tile;


public final class StateManager {
  public static final ShockAnimation shockAnimation = new ShockAnimation(
      null, 0.45f, Gdx.graphics.getWidth() / 50,
      18f, 1.2F);

  static {
    ObjectManager.add(shockAnimation);
  }

  public static void detectsGameOver() {
    boolean isPlacable = false;
    for (Shape shape : ShapeController.shapes) {
      if (shape != null && BoardController.isPlacableEntire(shape)) {
        isPlacable = true;
        break;
      }
    }

    if (!isPlacable) {
      System.out.println("Game Over: board is full.");
      TileController.reset();
      ShapeController.newRound();
    }
  }

  public static boolean isRemovable(MatrixType type, int index) {
    if (type == MatrixType.ROWS) {
      // 檢查該 row 的所有 column 是否都有 tile
      for (int col = 0; col < Tile.COLS; col++) {
        if (!TileController.hasTile(new BoardPosition(index, col))) {
          return false;
        }
      }
      return true;
    } else {
      // 檢查該 column 的所有 row 是否都有 tile
      for (int row = 0; row < Tile.ROWS; row++) {
        if (!TileController.hasTile(new BoardPosition(row, index))) {
          return false;
        }
      }
      return true;
    }
  }

  public static ArrayList<Integer> getRemovableRows(boolean[][] v) {
    ArrayList<Integer> rows = new ArrayList<>();
    if (v == null) return rows;

    // 確認 v 有足夠的列數
    int rowsCount = Math.min(v.length, Tile.ROWS);
    // 欄數應以 Tile.COLS 為主
    for (int c = 0; c < Tile.COLS; c++) {
      boolean allTrue = true;
      for (int r = 0; r < rowsCount; r++) {
        if (v[r] == null || v[r].length <= c || !v[r][c]) {
          allTrue = false;
          break;
        }
      }
      if (allTrue) rows.add(c);
    }
    return rows;
  }

  public static ArrayList<Integer> getRemovableCols(boolean[][] v) {
    ArrayList<Integer> cols = new ArrayList<>();
    if (v == null) return cols;

    // 安全檢查尺寸；若不符 Tile 常數則依 Tile 常數為準，避免 IndexOutOfBounds
    int rowsCount = Math.min(v.length, Tile.ROWS);
    for (int r = 0; r < rowsCount; r++) {
      boolean allTrue = true;
      // 如果這一列的欄數不足，也視為非可移除
      if (v[r] == null || v[r].length < Tile.COLS) {
        allTrue = false;
      } else {
        for (int c = 0; c < Tile.COLS; c++) {
          if (!v[r][c]) {
            allTrue = false;
            break;
          }
        }
      }
      if (allTrue) cols.add(r);
    }
    return cols;
  }

  public static void removeLines(List<Integer> lines, MatrixType type) {
    if (lines == null || lines.isEmpty()) return;

    if (type == MatrixType.ROWS) {
      for (int c : lines) {
        onRemoveLine();
        for (int r = 0; r < Tile.ROWS; r++) {
          BoardPosition pos = new BoardPosition(r, c);
          if (TileController.hasTile(pos)) {
            TileController.remove(pos);
          }
        }
      }
    } else if (type == MatrixType.COLS) {
      for (int r : lines) {
        onRemoveLine();
        for (int c = 0; c < Tile.COLS; c++) {
          BoardPosition pos = new BoardPosition(r, c);
          if (TileController.hasTile(pos)) {
            TileController.remove(pos);
          }
        }
      }
    } else {
      // 若有其他 MatrixType，暫時不處理
      // 可根據需求擴充
    }
  }

  private static void detectRemoveLine() {
    // 建立棋盤快照（避免在掃描時直接操作 TileController）
    boolean[][] snapshot = new boolean[Tile.ROWS][Tile.COLS];
    for (int r = 0; r < Tile.ROWS; r++) {
      for (int c = 0; c < Tile.COLS; c++) {
        snapshot[r][c] = TileController.hasTile(new BoardPosition(r, c));
      }
    }

    List<Integer> removableRows = getRemovableRows(snapshot);
    List<Integer> removableCols = getRemovableCols(snapshot);

    boolean withRemoveLine = !(removableCols.size() == 0 && removableRows.size() == 0);
    GameController.afterShapePlaced(withRemoveLine, removableCols.size() + removableRows.size());

    // 實際移除（會觸發 onRemoveLine）
    removeLines(removableRows, MatrixType.ROWS);
    removeLines(removableCols, MatrixType.COLS);
  }

  private static void onRemoveLine() {
    shockAnimation.start();
  }

  private static void detectShapeState() {
    boolean allUsed = true;

    for (int i = 0; i < ShapeController.shapes.length; i++) {
      if (ShapeController.shapes[i] != null) {
        allUsed = false;
        break;
      }
    }

    if (allUsed) {
      ShapeController.newRound();
    }
  }

  private static void detectChangeTheme() {
  }

  public static void onPlaceDone() {
    detectShapeState();
    detectRemoveLine();
    detectsGameOver();
    detectChangeTheme();
  }
}
