package dev.cas.game.block2.standard.object;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.cas.engine.GameInstance;
import com.cas.engine.annotation.LifeCircleControlledManually;
import com.cas.engine.component.Draggable;
import com.cas.engine.format.Size;
import com.cas.engine.utils.GraphicUtils;

import dev.cas.game.block2.standard.format.ShapeInfo;
import dev.cas.game.block2.standard.script.ShapeController;

@LifeCircleControlledManually
public final class Shape extends Draggable {
  public static final int SHAPE_DISPLAY_NUM = 3;
  public static final String ICON_FILE_NAME = "icon/block_128_9.png";
  public static final int BASE_UNIT_WIDTH = (int) (screenWidth() * 0.0625F);
  public static final int BASE_UNIT_HEIGHT = (int) (screenWidth() * 0.0625F);
  public static final int DISPLAY_SPACING = (int) (screenWidth() * 0.175F);

  public static final Pixmap BASE_ICON = GraphicUtils.loadAndAdjustPixmap(
      ICON_FILE_NAME, BASE_UNIT_WIDTH, BASE_UNIT_HEIGHT);
  public static final Pixmap BASE_DRAGGING_ICON = GraphicUtils.loadAndAdjustPixmap(
      ICON_FILE_NAME, Tile.WIDTH, Tile.HEIGHT);

  public final ShapeInfo info;
  public Pixmap draggingPixmap;
  public Texture draggingTexture;

  public Shape(String name, ShapeInfo info) {
    super(name);
    this.info = info;
  }

  @Override
  public void init() {
    initSize();
    initPos();
    super.initRange();
    // enlarge the touch/hit area so users can start dragging from a bigger region
    // around the displayed slot. Uses DISPLAY_SPACING and base unit height as padding.
    expandRange((int) (DISPLAY_SPACING / 2f), BASE_UNIT_HEIGHT * 2);
    super.initPixmap();
    initIcon();
    initDraggingPixmap();
    super.initTexture();
  }

  private void expandRange(int padX, int padY) {
    if (super.range == null || super.range.length < 4)
      return;

    int sx1 = Math.max(0, super.range[0] - padX);
    int sy1 = Math.max(0, super.range[1] - padY);
    int sx2 = Math.min(screenWidth(), super.range[2] + padX);
    int sy2 = Math.min(screenHeight(), super.range[3] + padY);

    super.range = new int[] { sx1, sy1, sx2, sy2 };
  }

  private void initSize() {
    int rx = BASE_UNIT_WIDTH * this.info.type().width;
    int ry = BASE_UNIT_HEIGHT * this.info.type().height;
    super.size = new Size(rx, ry);
  }

  private void initPos() {
    Vector2 anchor = ShapeController.positions[this.info.index()];
    float px = anchor.x - super.size.width() / 2f;
    float py = anchor.y - super.size.height() / 2f;
    super.pos = new Vector2(px, py);
  }

  private void initDraggingPixmap() {
    int[][] data = this.info.type().getData();

    int cols = data[0].length;
    int rows = data.length;
    this.draggingPixmap = new Pixmap(cols * Tile.WIDTH, rows * Tile.HEIGHT, Pixmap.Format.RGBA8888);

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        drawSection(col, row, Tile.WIDTH, Tile.HEIGHT, this.draggingPixmap, BASE_DRAGGING_ICON, data[row][col]);
      }
    }

    this.draggingTexture = new Texture(this.draggingPixmap);
  }

  private void initIcon() {
    int[][] data = this.info.type().getData();

    for (int row = 0; row < data.length; row++) {
      for (int col = 0; col < data[row].length; col++) {
        drawSection(col, row, BASE_UNIT_WIDTH, BASE_UNIT_HEIGHT, this.pixmap, BASE_ICON, data[row][col]);
      }
    }
  }

  private void drawSection(int col, int row, int unitWidth, int unitHeight, Pixmap pixmap, Pixmap icon, int situation) {
    switch (situation) {
      case 1 -> {
        int destX = col * unitWidth;
        int destY = pixmap.getHeight() - (row + 1) * unitHeight;
        pixmap.drawPixmap(icon, destX, destY);

      }
    }
  }

  @Override
  public void render() {
    if (super.isDragging) {
      ShapeController.onShapeDragging(this);
    }

    GameInstance.objectBatch.setColor(this.info.color());
    if (super.isDragging) {
      GameInstance.objectBatch.draw(this.draggingTexture, super.pos.x, super.pos.y);
    } else {
      GameInstance.objectBatch.draw(this.texture, super.pos.x, super.pos.y);
    }
    GraphicUtils.setBatchToWhite();
  }

  @Override
  public void onDragStop() {
    ShapeController.onShapeDragStop(this);
    returnOriginalPlace();
  }

  public void returnOriginalPlace() {
    initPos();
    super.initRange();
  }
}
