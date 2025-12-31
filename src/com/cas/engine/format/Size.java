package com.cas.engine.format;

import com.badlogic.gdx.Gdx;

public record Size(int width, int height) {
  public static final int HEIGHT_MAX = 50000;
  public static final int WIDTH_MAX = 50000;

  public static final Size ZERO = new Size(0, 0);
  public static final Size ENTIRE = new Size(Gdx.graphics.getWidth(), Gdx.graphics.getWidth());

  public Size {
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("height or width is negative");
    }

    if (width > WIDTH_MAX || height > HEIGHT_MAX) {
      throw new IllegalArgumentException("size is too big !");
    }
  }

  public static Size width(float rate) {
    int v = (int) (Gdx.graphics.getWidth() * rate);
    return new Size(v, v);
  }

  public static Size height(float rate) {
    int v = (int) (Gdx.graphics.getHeight() * rate);
    return new Size(v, v);
  }

  public static Size scale(float rate) {
    return new Size((int) (Gdx.graphics.getWidth() * rate), (int) (Gdx.graphics.getHeight() * rate));
  }
}
