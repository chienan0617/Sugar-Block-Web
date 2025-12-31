package dev.cas.game.block2.standard.script;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.cas.engine.annotation.Nullable;

import dev.cas.game.block2.standard.format.BoardPosition;
import dev.cas.game.block2.standard.object.Board;
import dev.cas.game.block2.standard.object.Shape;
import dev.cas.game.block2.standard.object.Tile;

public final class GestureController {

  @Nullable
  public static BoardPosition getBestPosition(Shape shape, Vector2 pos) {
    float cellSize = Tile.WIDTH + Board.INTERNAL;

    float px = pos.x - Board.START_POINT.x;// + Tile.WIDTH / 2F;
    float py = pos.y - Board.START_POINT.y + Board.BORDER;// - oriPos.y;

    int col = (int) Math.floor(px / cellSize);
    int row = (int) Math.floor(py / cellSize);

    // bounds check
    if (col < 0 || col >= Tile.COLS || row < 0 || row >= Tile.ROWS) {
      return null;
    }

    var boardPos = new BoardPosition(col, row);
    return findBestPlacementPosition(shape, boardPos);
  }

  public static BoardPosition findBestPlacementPosition(Shape shape, BoardPosition boardPos) {
    if (boardPos == null)
      return null;

    List<BoardPosition> cands = new ArrayList<>();
    addIfValid(cands, boardPos.x(), boardPos.y());
    addIfValid(cands, boardPos.x() + 1, boardPos.y());
    addIfValid(cands, boardPos.x(), boardPos.y() - 1);
    addIfValid(cands, boardPos.x() - 1, boardPos.y());
    addIfValid(cands, boardPos.x(), boardPos.y() + 1);

    BoardPosition bestPos = null;
    double bestDistanceSq = Double.MAX_VALUE;

    for (BoardPosition cand : cands) {
      if (SolutionFinder.isPlacableOn(shape, cand)) {
        double distSq = (cand.x() - boardPos.x()) * (cand.x() - boardPos.x())
            + (cand.y() - boardPos.y()) * (cand.y() - boardPos.y());
        if (distSq < bestDistanceSq) {
          bestDistanceSq = distSq;
          bestPos = cand;
        }
      }
    }

    return bestPos;
  }

  private static void addIfValid(List<BoardPosition> list, int x, int y) {
    if (x < 0 || x >= Tile.COLS || y < 0 || y >= Tile.ROWS)
      return;
    list.add(new BoardPosition(x, y));
  }
}
