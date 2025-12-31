package com.cas.engine.utils;

import com.badlogic.gdx.Gdx;

public final class ScreenUtils extends Utils {
  public static int getWidth() {
    return Gdx.graphics.getWidth();
  }

  public static int getHeight() {
    return Gdx.graphics.getHeight();
  }
}
