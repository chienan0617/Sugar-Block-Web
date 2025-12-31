package dev.cas.game.block2.standard.script;

import dev.cas.game.block2.standard.format.BoardPosition;
import dev.cas.game.block2.standard.format.TileInfo;
import dev.cas.game.block2.standard.format.TileType;
import dev.cas.game.block2.standard.object.Shape;
import dev.cas.game.block2.standard.object.Tile;

public final class SolutionFinder {
  public static boolean isPlacableOn(Shape shape, BoardPosition pos) {
    int[][] data = shape.info.type().getData();

    int rows = data.length;
    int cols = data[0].length;

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        if (data[r][c] != 0) {
          int boardX = (int) pos.x() + c;
          int boardY = (int) pos.y() + r;

          // check bounds
          if (boardX < 0 || boardX >= Tile.COLS || boardY < 0 || boardY >= Tile.ROWS)
            return false;

          if (TileController.hasTile(new BoardPosition(boardX, boardY)))
            return false;
        }
      }
    }
    return true;
  }

  public static boolean placeShapeAt(Shape shape, BoardPosition boardPos) {
    int[][] data = shape.info.type().getData();
    int rows = data.length;
    int cols = data[0].length;

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        if (data[r][c] != 0) {
          int px = (int) (boardPos.x() + c);
          int py = (int) (boardPos.y() + r);

          BoardPosition pos = new BoardPosition(px, py);

          Tile tile = new Tile(null, new TileInfo(pos, TileType.NORMAL, shape.info.color()));
          TileController.put(pos, tile);
        }
      }
    }

    return true;
  }
}
