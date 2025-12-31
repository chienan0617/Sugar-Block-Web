package com.cas.engine.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.cas.engine.GameInstance;
import com.cas.engine.format.Size;

public class Drawable extends Sized implements DrawableBase {
  public Pixmap pixmap;
  public Texture texture;

  public Drawable(String name, Size size, Vector2 pos) {
    super(name, size, pos);
    this.pixmap = new Pixmap(super.size.width(), super.size.height(), Pixmap.Format.RGBA8888);
  }

  public Drawable(String name, Size size, Vector2 pos, boolean preInitPixmap) {
    super(name, size, pos);
    // this.pixmap = new Pixmap(super.size.width(), super.size.height(),
    // Pixmap.Format.RGBA8888);
  }

  public Drawable(String name) {
    super(name);
  }

  public Drawable setColor(Color color) {
    this.pixmap.setColor(color);
    return this;
  }

  @Override
  public void init() {
    initPixmap();
  }

  @Override
  public void initPixmap() {
    this.pixmap = new Pixmap(super.size.width(), super.size.height(), Pixmap.Format.RGBA8888);
  }

  @Override
  public void initTexture() {
    this.texture = new Texture(this.pixmap);
  }

  @Override
  public void render() {
    if (this.texture != null) {
      GameInstance.objectBatch.draw(this.texture, super.pos.x, super.pos.y);
    }
  }
}
