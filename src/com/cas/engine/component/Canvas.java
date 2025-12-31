package com.cas.engine.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.cas.engine.format.Size;

public class Canvas extends Drawable {
  public boolean done = false;

  public Canvas(String name, Size size, Vector2 pos) {
    super(name, size, pos);
  }

  public Canvas(String name, Size size, Vector2 pos, boolean preInitPixmap) {
    super(name, size, pos, preInitPixmap);
  }

  public Canvas(String name) {
    super(name);
  }

  public Canvas setColor(Color color) {
    super.pixmap.setColor(color);
    return this;
  }

  public Canvas fill() {
    super.pixmap.fillRectangle(0, 0, super.size.width(), super.size.height());
    return this;
  }

  public Canvas fill(Color color) {
    setColor(color);
    fill();
    return this;
  }

  public Canvas fillRect(int x, int y, int w, int h) {
    super.pixmap.fillRectangle(x, y, w, h);
    return this;
  }

  /**
   * 新增：繪製圓角矩形
   * 原理：繪製兩個十字交叉的矩形，然後在四個角落補上圓形
   */
  public Canvas fillRoundedRect(int x, int y, int w, int h, int radius) {
    // 防止半徑過大導致破圖
    if (radius > w / 2)
      radius = w / 2;
    if (radius > h / 2)
      radius = h / 2;

    // 1. 繪製中間橫向矩形 (扣除左右圓角寬度)
    super.pixmap.fillRectangle(x + radius, y, w - 2 * radius, h);

    // 2. 繪製中間直向矩形 (扣除上下圓角高度)
    super.pixmap.fillRectangle(x, y + radius, w, h - 2 * radius);

    // 3. 繪製四個角落的圓形
    // 左上
    super.pixmap.fillCircle(x + radius, y + radius, radius);
    // 右上
    super.pixmap.fillCircle(x + w - radius, y + radius, radius);
    // 左下
    super.pixmap.fillCircle(x + radius, y + h - radius, radius);
    // 右下
    super.pixmap.fillCircle(x + w - radius, y + h - radius, radius);

    return this;
  }

  public Canvas fillRoundedRect(int r) {
    return fillRoundedRect(0, 0, super.size.width(), super.size.height(), r);
  }

  public Canvas done() {
    super.texture = new Texture(super.pixmap, true); // true 開啟 MipMap 讓圓角邊緣更平滑一點(選用)
    // Texture filter 設定可以讓縮放時圓角不那麼鋸齒
    super.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    this.done = true;
    return this;
  }
}
