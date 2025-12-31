package com.cas.engine.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.cas.engine.format.Size;
import com.cas.engine.object.GameObject;

public class Sized extends GameObject {
  public Size size = Size.ZERO;
  public Vector2 pos = new Vector2();
  public int[] range;

  public Sized(String name, Size size, Vector2 pos) {
    super(name);
    this.size = size;
    this.pos = pos;

    // initRange();
  }

  public Sized(String name) {
    super(name);
  }

  public void initRange() {
    range = new int[] {
      (int) pos.x,
      (int) (screenHeight() - (pos.y + size.height())),
      (int) (pos.x + size.width()),
      (int) (screenHeight() - pos.y)
    };
  }

  public static int screenWidth() {
    return Gdx.graphics.getWidth();
  }

  public static int screenHeight() {
    return Gdx.graphics.getHeight();
  }
}
