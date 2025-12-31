package dev.cas.game.block2.dropping.object;

import com.cas.engine.GameInstance;
import com.cas.engine.annotation.LifeCircleControlledManually;
import com.cas.engine.annotation.NotNull;
import com.cas.engine.component.Canvas;
import com.cas.engine.component.Text;
import com.cas.engine.core.ObjectManager;
import com.cas.engine.format.Size;
import com.cas.engine.format.TextAlign;

import dev.cas.game.block2.dropping.format.NumberBlockData;
import dev.cas.game.block2.dropping.script.NumberBlockLogic;

@LifeCircleControlledManually
public class NumberBlock extends Canvas {
  public static final int ROWS = 5;
  public static final int COLS = 5;
  public static final int SIZE = BlockBoard.TILE_SIZE;

  public final NumberBlockData data;
  public final Text text;

  public NumberBlock(@NotNull final NumberBlockData data) {
    super(null);
    this.data = data;
    super.size = new Size(SIZE, SIZE);
    super.pos = NumberBlockLogic.getAbsolutePosition(this.data.pos());
    this.text = new Text(null, this.pos)
        .setText(Integer.toString(this.data.value()))
        .setScale(1.875F)
        .setAlign(TextAlign.ALIGN_CENTER);
    this.text.setPos(pos.cpy().add(SIZE / 2F, SIZE / 2F + this.text.getTextHeight() / 2F));
    ObjectManager.ensureStart(this.text);
  }

  @Override
  public void init() {
    super.initRange();
    super.initPixmap();
    drawTexture();
    super.initTexture();
  }

  private void drawTexture() {
    super.setColor(this.data.color()).fillRoundedRect(5).done();
  }

  @Override
  public void render() {
    GameInstance.objectBatch.draw(super.texture, super.pos.x, super.pos.y);
    this.text.render();
  }

  @Override
  public void dispose() {
    ObjectManager.ensureEnd(this.text);
    remove(this.text);
  }
}
