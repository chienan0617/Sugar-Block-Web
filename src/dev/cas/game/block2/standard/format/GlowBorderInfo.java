package dev.cas.game.block2.standard.format;

import com.badlogic.gdx.graphics.Color;
import com.cas.engine.exception.ThrowError;

import dev.cas.game.block2.standard.object.Tile;

public record GlowBorderInfo(MatrixType type, int unit, Color color) {
  public GlowBorderInfo {
    ThrowError.checkOrThrow(type);
    ThrowError.checkOrThrow(color);

    if (unit >= Tile.ROWS || unit >= Tile.COLS) {
      ThrowError.indexOutOfBounds(unit);
    }
  }

  public int getWidth() {
    // if highlighting a column, width spans all columns; if highlighting a row, width is single tile width
    return this.type == MatrixType.COLS ? Tile.WIDTH * Tile.COLS : Tile.WIDTH;
  }

  public int getHeight() {
    // if highlighting a column, height is single tile height; if highlighting a row, height spans all rows
    return this.type == MatrixType.COLS ? Tile.HEIGHT : Tile.HEIGHT * Tile.ROWS;
  }
}
