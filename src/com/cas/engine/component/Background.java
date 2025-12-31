package com.cas.engine.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.cas.engine.object.GameObject;

public class Background extends GameObject {
  public Color color;

  public Background(String name, Color color) {
    super(name);
    this.color = color;
  }

  @Override
  public void render() {
    // GameInstance.objectBatch.draw(null, order, ID);
    ScreenUtils.clear(this.color);
  }
}
