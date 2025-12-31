package dev.cas.game.block2.standard.script;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.cas.engine.GameInstance;
import com.cas.engine.utils.GraphicUtils;

import dev.cas.game.block2.standard.format.BoardPosition;
import dev.cas.game.block2.standard.object.Shape;
import dev.cas.game.block2.standard.object.Tile;

public final class SnapshotController {
  public static void tryShowSnapshot(Shape shape, BoardPosition pos) {
    if (SolutionFinder.isPlacableOn(shape, pos)) {
      showSnapshotAt(shape, pos);
    }
  }

  public static void showSnapshotAt(Shape shape, BoardPosition boardPos) {
    int[][] data = shape.info.type().getData();

    int rows = data.length; // height
    int cols = data[0].length; // width

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        if (data[r][c] == 0)
          continue; // only preview filled cells

        int boardRow = boardPos.x() + c;
        int boardCol = boardPos.y() + r;

        // skip out-of-bounds preview positions
        if (boardRow < 0 || boardRow >= Tile.ROWS || boardCol < 0 || boardCol >= Tile.COLS)
          continue;

        BoardPosition nbp = new BoardPosition(boardRow, boardCol);
        Vector2 renderPos = BoardController.getRenderPosition(nbp);

        Color cc = shape.info.color();

        GameInstance.objectBatch.setColor(cc.r, cc.g, cc.b, 0.5F);
        GameInstance.objectBatch.draw(Tile.ICON, renderPos.x, renderPos.y);
        GraphicUtils.setBatchToWhite();
      }
    }
  }
}
