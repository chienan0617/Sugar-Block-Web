package dev.cas.game.block2.standard.format;

import com.badlogic.gdx.graphics.Color;
import com.cas.engine.exception.ThrowError;

import dev.cas.game.block2.standard.object.Shape;

public record ShapeInfo(int index, ShapeType type, Color color) {
  public ShapeInfo {
    if (index < 0 || index >= Shape.SHAPE_DISPLAY_NUM) {
      ThrowError.indexOutOfBounds(index);
    }

    if (type == null) {
      ThrowError.nullPointer(type);
    }

    if (color == null) {
      ThrowError.nullPointer(color);
    }
  }
}
