package dev.cas.game.block2.standard.format;

import com.badlogic.gdx.graphics.Color;
import com.cas.engine.exception.ThrowError;

public final record TileInfo(BoardPosition pos, TileType type, Color color) {
  public TileInfo {
    if (color == null) {
      ThrowError.nullPointer(color);
    }

    if (type == null) {
      ThrowError.nullPointer(type);
    }
  }
}
