package com.cas.engine.component;

import com.cas.engine.object.GameObject;
import com.cas.engine.object.GamePiece;

public class Scene extends GameObject {
  public GamePiece[] gamePieces;
  public String sceneName;

  public Scene(String name, String sceneName, GamePiece[] gamePieces) {
    super(name);
    this.sceneName = sceneName;
    this.gamePieces = gamePieces;
  }

  public void loadPieces() {
    for (GamePiece piece : gamePieces) {
      add(piece);
    }
  }

  public void unloadPieces() {
    for (GamePiece gamePiece : gamePieces) {
      remove(gamePiece);
    }
  }
}
