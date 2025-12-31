package dev.cas.game.block2.standard.script;

import dev.cas.game.block2.standard.format.BoardPosition;
import dev.cas.game.block2.standard.object.Shape;
import dev.cas.game.block2.standard.object.Tile;

public final class TileEmulator {
  public static boolean[][] snapshotGrid() {
    boolean[][] g = new boolean[Tile.ROWS][Tile.COLS];
    for (int row = 0; row < Tile.ROWS; row++) {
      for (int col = 0; col < Tile.COLS; col++) {
        // TileController.hasTile expects (row, col)
        g[row][col] = TileController.hasTile(new BoardPosition(row, col));
      }
    }
    return g;
  }

  // isPlacableOnGrid - baseRow, baseCol（不要把 col-offset 加到 row 上）
  private static boolean isPlacableOnGrid(Shape shape, int baseRow, int baseCol, boolean[][] g) {
    int[][] data = shape.info.type().getData();
    int rows = g.length; // Tile.ROWS
    int cols = g[0].length; // Tile.COLS

    for (int r = 0; r < data.length; r++) { // r = data row
      for (int c = 0; c < data[0].length; c++) { // c = data col
        if (data[r][c] != 0) {
          int rowIndex = baseRow + r;
          int colIndex = baseCol + c;
          if (rowIndex < 0 || rowIndex >= rows || colIndex < 0 || colIndex >= cols)
            return false;
          if (g[rowIndex][colIndex])
            return false;
        }
      }
    }
    return true;
  }

  // placeOnGrid - 同上，row-major
  private static void placeOnGrid(Shape shape, int baseRow, int baseCol, boolean[][] g) {
    int[][] data = shape.info.type().getData();
    for (int r = 0; r < data.length; r++) {
      for (int c = 0; c < data[0].length; c++) {
        if (data[r][c] != 0) {
          int rowIndex = baseRow + r;
          int colIndex = baseCol + c;
          g[rowIndex][colIndex] = true;
        }
      }
    }
  }

  /** 模擬消除：找到滿列或滿行並清除（與 StateManager.detectRemoveLine 行為對應） */
  private static void detectRemoveLineOnGrid(boolean[][] g) {
    int rows = g.length;
    int cols = g[0].length;

    // collect full rows (row == x)
    boolean[] fullRow = new boolean[rows];
    for (int x = 0; x < rows; x++) {
      boolean all = true;
      for (int y = 0; y < cols; y++) {
        if (!g[x][y]) {
          all = false;
          break;
        }
      }
      fullRow[x] = all;
    }

    // collect full cols (col == y)
    boolean[] fullCol = new boolean[cols];
    for (int y = 0; y < cols; y++) {
      boolean all = true;
      for (int x = 0; x < rows; x++) {
        if (!g[x][y]) {
          all = false;
          break;
        }
      }
      fullCol[y] = all;
    }

    // clear them
    for (int x = 0; x < rows; x++) {
      if (fullRow[x]) {
        for (int y = 0; y < cols; y++)
          g[x][y] = false;
      }
    }
    for (int y = 0; y < cols; y++) {
      if (fullCol[y]) {
        for (int x = 0; x < rows; x++)
          g[x][y] = false;
      }
    }
  }

  /**
   * 嘗試在 grid 上以任意順序放下 shapes（回溯）。
   * 回傳 true 表示存在一組（位置+順序）能把所有 shapes 放下。
   */
  public static boolean canPlaceAnyOrderSequence(Shape[] shapes) {
    boolean[][] g = snapshotGrid();
    boolean[] used = new boolean[shapes.length];
    return canPlaceRec(shapes, used, g);
  }

  private static boolean canPlaceRec(Shape[] shapes, boolean[] used, boolean[][] g) {
    // 找到所有已放的數量
    int placedCount = 0;
    for (boolean b : used)
      if (b)
        placedCount++;
    if (placedCount == shapes.length)
      return true;

    int rows = g.length;
    int cols = g[0].length;

    // 嘗試選擇尚未使用的 shape 放到 grid 的任一合法位置（回溯）
    for (int si = 0; si < shapes.length; si++) {
      if (used[si])
        continue;
      Shape s = shapes[si];

      // try all possible base positions
      for (int bx = 0; bx < rows; bx++) {
        for (int by = 0; by < cols; by++) {
          if (isPlacableOnGrid(s, bx, by, g)) {
            // copy grid
            boolean[][] copy = new boolean[rows][cols];
            for (int i = 0; i < rows; i++)
              System.arraycopy(g[i], 0, copy[i], 0, cols);

            // place & detect removals
            placeOnGrid(s, bx, by, copy);
            detectRemoveLineOnGrid(copy);

            used[si] = true;
            boolean ok = canPlaceRec(shapes, used, copy);
            used[si] = false;

            if (ok)
              return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * 模擬在 pos 放下 shape 後的格子狀態。
   * 如果不能放置（越界或衝突）或 pos 為 null，回傳原本的 snapshotGrid()（不變）。
   */
  public static boolean[][] emulateShapePutted(Shape shape, BoardPosition pos, boolean removeAutomatically) {
    boolean[][] g = snapshotGrid();

    if (pos == null || shape == null) {
      return g; // 無法模擬，回傳原本的
    }

    int baseRow = pos.x();
    int baseCol = pos.y();

    if (!isPlacableOnGrid(shape, baseRow, baseCol, g)) {
      return g;
    }

    // 複製格子並放置、模擬消除後回傳
    int rows = g.length;
    int cols = g[0].length;
    boolean[][] copy = new boolean[rows][cols];
    for (int i = 0; i < rows; i++) {
      System.arraycopy(g[i], 0, copy[i], 0, cols);
    }

    placeOnGrid(shape, baseRow, baseCol, copy);

    // if (removeAutomatically) {
    //   detectRemoveLineOnGrid(copy);
    // }

    return copy;
  }
}
