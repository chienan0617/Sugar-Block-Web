package dev.cas.game.block2.standard.object;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.cas.engine.GameInstance;
import com.cas.engine.annotation.LifeCircleControlledManually;
import com.cas.engine.component.Drawable;
import com.cas.engine.format.Size;
import com.cas.engine.utils.GraphicUtils;

import dev.cas.game.block2.standard.format.TileInfo;
import dev.cas.game.block2.standard.script.BoardController;

@LifeCircleControlledManually
public final class Tile extends Drawable {
  public static final int ROWS = 8;
  public static final int COLS = 8;
  public static final int WIDTH = (Board.WIDTH - (Board.BORDER * 2) - (COLS - 1) * Board.INTERNAL) / COLS;
  public static final int HEIGHT = WIDTH;
  public static final String ICON_FILE_NAME = "icon/block_17t.png";
  public static final Pixmap ICON_PIXMAP = GraphicUtils.loadAndAdjustPixmap(ICON_FILE_NAME, WIDTH, HEIGHT);
  public static final Texture ICON = new Texture(ICON_PIXMAP);

  public final TileInfo info;

  public Tile(String name, TileInfo info) {
    super(name);
    this.info = info;
  }

  @Override
  public void init() {
    initSize();
    initPos();
    initTex();
  }

  private void initPos() {
    super.pos = BoardController.getRenderPosition(this.info.pos());
  }

  private void initSize() {
    super.size = new Size(WIDTH, HEIGHT);
  }

  private void initTex() {
    super.texture = new Texture(ICON_PIXMAP);
  }

  @Override
  public void render() {
    GameInstance.objectBatch.setColor(this.info.color());
    GameInstance.objectBatch.draw(this.texture, this.pos.x, this.pos.y);
    GraphicUtils.setBatchToWhite();
  }
}
