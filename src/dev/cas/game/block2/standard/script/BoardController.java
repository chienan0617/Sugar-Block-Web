package dev.cas.game.block2.standard.script;

import com.badlogic.gdx.math.Vector2;

import dev.cas.game.block2.standard.format.BoardPosition;
import dev.cas.game.block2.standard.object.Board;
import dev.cas.game.block2.standard.object.Shape;
import dev.cas.game.block2.standard.object.Tile;

public final class BoardController {
  public static Vector2 getRenderPosition(BoardPosition pos) {
    int availableWidth = Board.WIDTH - (Board.BORDER * 2);
    int totalGapWidth = (Tile.COLS - 1) * Board.INTERNAL;
    int tileSize = (availableWidth - totalGapWidth) / Tile.COLS;

    float bx = Board.START_POINT.x + Board.BORDER + pos.x() * (tileSize + Board.INTERNAL);
    float by = Board.START_POINT.y + Board.BORDER + pos.y() * (tileSize + Board.INTERNAL);

    return new Vector2(bx, by);
  }

  public static boolean isPlacableEntire(Shape shape) {
    for (int x = 0; x < Tile.ROWS; x++) {
      for (int y = 0; y < Tile.COLS; y++) {
        if (SolutionFinder.isPlacableOn(shape, new BoardPosition(x, y))) {
          return true;
        }
      }
    }
    return false;
  }

  public static boolean attemptToPlaceShape(Shape shape, BoardPosition pos) {
    if (SolutionFinder.isPlacableOn(shape, pos)) {
      // BoardController.attemptToPlaceShape(shape, pos)
      if (!SolutionFinder.placeShapeAt(shape, pos)) {
        // throw new UnknownError();
      }
      return true;
    }
    return false;
  }
}
