package com.cas.engine.object;

import com.cas.engine.GameInstance;
import com.cas.engine.annotation.DebugOnly;

public class GamePiece implements GameBase {
  public long ID;
  public boolean visible = true;
  public boolean initYet = false;
  public int order = 1;

  public GamePiece() {
    this.ID = getID();
  }

  public static long getID() {
    return GameInstance.RANDOM.nextLong();
  }

  @Override
  public void awake() {
  }

  @Override
  public void init() {
  }

  @Override
  public void update(float delta) {
  }

  @Override
  public void render() {
  }

  /**
   * Return true when this piece should be drawn in screen-space (UI/HUD)
   * using the `superiorBatch`. By default pieces are world-space and
   * follow the camera.
   */
  public boolean isScreenSpace() {
    return false;
  }

  @Override
  public void end() {
  }

  @Override
  public void dispose() {
  }

  @Override
  public void resize(int width, int height) {
  }

  @Override
  public void resume() {
  }

  @Override
  public void pause() {
  }

  @DebugOnly
  @Override
  public void debugInit() {
  }

  @DebugOnly
  @Override
  public void debugUpdate(float delta) {
  }
}
