package dev.cas.game.block2.standard.script;

import com.cas.engine.annotation.DebugOnly;
import com.cas.engine.annotation.Nullable;
import com.cas.engine.core.ObjectManager;
import com.cas.engine.graphic.Colors;
import com.cas.engine.object.GamePiece;
import com.cas.engine.utils.Mth;

import dev.cas.game.block2.standard.format.BoardPosition;
import dev.cas.game.block2.standard.format.TileInfo;
import dev.cas.game.block2.standard.format.TileType;
import dev.cas.game.block2.standard.object.Tile;

public final class TileController extends GamePiece {
  private static final Tile[][] tiles = new Tile[Tile.ROWS][Tile.COLS];

  public static void put(BoardPosition pos, Tile tile) {
    ObjectManager.ensureStart(tile);
    tiles[pos.x()][pos.y()] = tile;
  }

  public static void remove(BoardPosition pos) {
    ObjectManager.ensureEnd(tiles[pos.x()][pos.y()]);
    tiles[pos.x()][pos.y()] = null;
  }

  @Nullable
  public static Tile get(BoardPosition pos) {
    return tiles[pos.x()][pos.y()];
  }

  public static boolean hasTile(BoardPosition pos) {
    return !(tiles[pos.x()][pos.y()] == null);
  }

  @DebugOnly
  public static void newRandom() {
    int x = Mth.nextInt(Tile.ROWS);
    int y = Mth.nextInt(Tile.COLS);

    TileInfo info = new TileInfo(new BoardPosition(x, y), TileType.NORMAL, Colors.random());
    put(new BoardPosition(x, y), new Tile(null, info));
  }

  @Override
  public void init() {
    for (int i = 0; i < 40; i++) {
      newRandom();
    }
  }

  @Override
  public void render() {
    for (int x = 0; x < tiles.length; x++) {
      for (int y = 0; y < tiles[x].length; y++) {
        if (tiles[x][y] != null) {
          tiles[x][y].render();
        }
      }
    }
  }

  @Override
  public void update(float delta) {
    for (int x = 0; x < tiles.length; x++) {
      for (int y = 0; y < tiles[x].length; y++) {
        if (tiles[x][y] != null)
          tiles[x][y].update(delta);
      }
    }
  }

  public static void reset() {
    for (int x = 0; x < tiles.length; x++) {
      for (int y = 0; y < tiles[x].length; y++) {
        tiles[x][y] = null;
      }
    }
  }
}
