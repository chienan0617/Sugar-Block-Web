package com.cas.engine.object;

public final class ObjectLifeCycle {
  public static <T extends GamePiece> void updateObjects(T[][] obj, float delta) {
    for (int i = 0; i < obj.length; i++) {
      for (int j = 0; j < obj[i].length; j++) {
        GamePiece piece = obj[i][j];
        if (piece != null) {
          piece.update(delta);
        }
      }
    }
  }

  public static <T extends GamePiece> void renderObjects(T[][] obj) {
    for (int i = 0; i < obj.length; i++) {
      for (int j = 0; j < obj[i].length; j++) {
        GamePiece piece = obj[i][j];
        if (piece != null) {
          piece.render();
        }
      }
    }
  }
}
