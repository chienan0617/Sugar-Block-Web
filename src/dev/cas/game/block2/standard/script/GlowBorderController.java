package dev.cas.game.block2.standard.script;

import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.List;

import com.cas.engine.GameInstance;
import com.cas.engine.core.ObjectManager;
import com.cas.engine.object.GamePiece;
import com.cas.engine.utils.GraphicUtils;

import dev.cas.game.block2.standard.format.BoardPosition;
import dev.cas.game.block2.standard.format.GlowBorderInfo;
import dev.cas.game.block2.standard.format.MatrixType;
import dev.cas.game.block2.standard.object.Board;
import dev.cas.game.block2.standard.object.GlowBorder;
import dev.cas.game.block2.standard.object.Shape;
import dev.cas.game.block2.standard.object.Tile;

public class GlowBorderController extends GamePiece {
  public static final GlowBorder borderCols = new GlowBorder(null, MatrixType.COLS);
  public static final GlowBorder borderRows = new GlowBorder(null, MatrixType.ROWS);
  private static final List<GlowBorderInfo> activeGlows = new ArrayList<>();

  public static void addToRenderAt(GlowBorderInfo data) {
    boolean isRow = data.type() == MatrixType.ROWS;
    int unit = data.unit();
    int px = isRow ? unit : 0;
    int py = isRow ? 0 : unit;
    Vector2 pos = getGlowBorderRenderPos(new BoardPosition(py, px));

    GameInstance.objectBatch.setColor(data.color());
    GameInstance.objectBatch.draw(isRow ? borderRows.texture : borderCols.texture, pos.x, pos.y);
    GraphicUtils.setBatchToWhite();
  }

  public static Vector2 getGlowBorderRenderPos(BoardPosition oriPos) {
    Vector2 pos = BoardController.getRenderPosition(oriPos);

    pos.y -= GlowBorder.BORDER -Tile.ROWS * Board.INTERNAL;
    pos.x -= GlowBorder.BORDER;
    return pos;
  }

  public static int getWidth(MatrixType type) {
    return type == MatrixType.COLS ? Tile.WIDTH * Tile.COLS : Tile.WIDTH - Board.INTERNAL * Tile.ROWS;
  }

  public static int getHeight(MatrixType type) {
    return type == MatrixType.COLS ? Tile.HEIGHT - Board.INTERNAL * Tile.ROWS : Tile.HEIGHT * Tile.ROWS;
  }

  public static void tryShowGlowBorder(Shape shape, BoardPosition bestPos) {
    // reset any previous highlights
    activeGlows.clear();

    if (shape == null || bestPos == null)
      return;

    // only consider if the shape can be placed here
    if (!SolutionFinder.isPlacableOn(shape, bestPos))
      return;

    // Build a snapshot of current board occupancy, then simulate placement
    boolean[][] snapshot = new boolean[Tile.ROWS][Tile.COLS];
    for (int r = 0; r < Tile.ROWS; r++) {
      for (int c = 0; c < Tile.COLS; c++) {
        snapshot[r][c] = TileController.hasTile(new BoardPosition(r, c));
      }
    }

    int[][] data = shape.info.type().getData();
    int rows = data.length;
    int cols = data[0].length;

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        if (data[r][c] == 0)
          continue;

        int boardX = bestPos.x() + c;
        int boardY = bestPos.y() + r;

        if (boardX < 0 || boardX >= Tile.ROWS || boardY < 0 || boardY >= Tile.COLS)
          continue;

        snapshot[boardX][boardY] = true; // simulate placing this cell
      }
    }

    // ask StateManager which rows/cols would be removable after this placement
    var removableCols = StateManager.getRemovableRows(snapshot); // returns column indices
    var removableRows = StateManager.getRemovableCols(snapshot); // returns row indices

    // Map them into glow infos consistent with detect/remove logic
    for (int colIndex : removableCols) {
      activeGlows.add(new GlowBorderInfo(MatrixType.ROWS, colIndex, shape.info.color()));
    }

    for (int rowIndex : removableRows) {
      activeGlows.add(new GlowBorderInfo(MatrixType.COLS, rowIndex, shape.info.color()));
    }
  }

  @Override
  public void render() {
    if (activeGlows.isEmpty())
      return;

    for (GlowBorderInfo info : activeGlows) {
      addToRenderAt(info);
    }
  }

  @Override
  public void init() {
    // ensure the border canvases are initialized so textures exist
    ObjectManager.ensureStart(borderCols);
    ObjectManager.ensureStart(borderRows);
  }

  public static void clear() {
    activeGlows.clear();
  }
}
