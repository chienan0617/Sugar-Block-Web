package dev.cas.game.block2.dropping.format;

import com.badlogic.gdx.graphics.Color;
import com.cas.engine.exception.ThrowError;
import com.cas.engine.utils.Mth;

public record NumberBlockData(int value, Color color, BlockBoardPosition pos) {
  public NumberBlockData {
    if (value <= 0) {
      ThrowError.indexOutOfBounds(value);
    }

    if (color == null) {
      ThrowError.nullPointer(color);
    }

    if (pos == null) {
      ThrowError.nullPointer(pos);
    }
  }

  public int getLogBaseValue() {
    return (int) Mth.log(value, 2);
  }
}
