package dev.cas.game.block2.standard.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.cas.engine.GameInstance;
import com.cas.engine.annotation.LifeCircleControlledManually;
import com.cas.engine.component.Canvas;

import dev.cas.game.block2.standard.format.MatrixType;
import dev.cas.game.block2.standard.script.GlowBorderController;

@LifeCircleControlledManually
public final class GlowBorder extends Canvas {
  public static final int BORDER = 40;

  public final MatrixType type;
  // public final Color color;

  public GlowBorder(String name, MatrixType type) {
    super(name);
    this.type = type == MatrixType.COLS ? MatrixType.ROWS : MatrixType.COLS;
    // this.color = color;
  }

  @Override
  public void init() {
    initPixmap();
    drawBorder();
    super.initTexture();
  }

  @Override
  public void initPixmap() {
    super.pixmap = new Pixmap(
        GlowBorderController.getWidth(this.type) + BORDER * 2,
        GlowBorderController.getHeight(this.type) + BORDER * 2,
        Pixmap.Format.RGBA8888);
  }

  private void drawBorder() {
    Color t = Color.WHITE;
    int n = Board.INTERNAL;
    boolean p = this.type == MatrixType.COLS;
    float s = 5.0F; // 衰變速度

    for (int i = 0; i < BORDER; i++) {
      float ex = (float) Math.pow(2, s * (float) i / (BORDER - 1));
      float gr = 1 - (ex - (float) Math.pow(2, s)) / (1 - (float) Math.pow(2, s));

      Color c = new Color(t.r, t.g, t.b, gr);
      super.pixmap.setColor(c);

      int drawX = i;
      int drawY = i;
      int width = pixmap.getWidth() - i * 2 + (p ? 0 : Tile.ROWS * n);
      int height = pixmap.getHeight() - i * 2 + (p ? Tile.COLS * n : 0);

      super.pixmap.drawRectangle(drawX, drawY, width, height);
    }
  }

  @Override
  public void render() {
    // debug("drawing");
    // GameInstance.objectBatch.setColor(this.color);
    GameInstance.objectBatch.draw(super.texture, super.pos.x, super.pos.y);
    // GraphicUtils.setBatchToWhite();
  }
}
