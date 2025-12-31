package dev.cas.game.block2.dropping.format;

import com.cas.engine.exception.ThrowError;

import dev.cas.game.block2.dropping.object.NumberBlock;

public record BlockBoardPosition(int px, int py) {
  public BlockBoardPosition {
    if (px >= NumberBlock.ROWS || px < 0) {
      ThrowError.indexOutOfBounds(px);
    }

    if (py >= NumberBlock.COLS || py < 0) {
      ThrowError.indexOutOfBounds(py);
    }
  }
}
