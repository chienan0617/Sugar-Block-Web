package dev.cas.game.block2.standard.format;

import com.cas.engine.exception.ThrowError;

import dev.cas.game.block2.standard.object.Tile;

public record BoardPosition(int x, int y) {
  public BoardPosition {
    if (x < 0 || x >= Tile.ROWS) {
      ThrowError.indexOutOfBounds(x);
    }

    if (y < 0 || y >= Tile.COLS) {
      ThrowError.indexOutOfBounds(y);
    }
  }
}
