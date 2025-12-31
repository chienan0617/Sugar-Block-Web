package dev.cas.game.block2.dropping.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.cas.engine.component.Canvas;
import com.cas.engine.format.Size;
import com.cas.engine.graphic.Colors;
import com.cas.engine.utils.Mth;

public class Board extends Canvas {
  public static final float PADDING = screenWidth() * 0.0625F;
  public static final float BORDER = screenWidth() * 0.02F;
  public static final float HEIGHT_RATE = 0.2125F;
  public static final float INTERNAL = 2.5F;
  public static final int BORDER_RADIUS = 25;
  public static final Color BORDER_COLOR = new Color(54 / 255F, 60 / 255F, 64 / 255F, 1F);
  public static final Color INTERNAL_COLOR = new Color(30 / 255F, 34 / 255F, 35 / 255F, 1F);

  public static int[] RANGE;
  public static Vector2 POS;
  public static Size SIZE;

  private int tileSize;
  private float innerWidth;
  private float innerHeight;

  public Board(String name) {
    super(name);
  }

  @Override
  public void init() {
    initSize();
    initPos();
    super.initRange();
    super.initPixmap();
    drawBoard();
    super.initTexture();
  }

  private void initSize() {
    // outerWidth 留下 PADDING + BORDER 在左右兩邊
    float outerWidth = screenWidth() - (PADDING + BORDER) * 2f;
    // innerWidth 為可放格子的區域（再扣掉 Board 自己的 BORDER）
    innerWidth = outerWidth - BORDER * 2f;

    tileSize = (int) Math.floor((innerWidth - (Block.ROWS - 1) * INTERNAL) / Block.ROWS);
    innerHeight = tileSize * Block.COLS + INTERNAL * (Block.COLS - 1);
    float outerHeight = innerHeight + BORDER * 2f;

    SIZE = new Size(Mth.r(outerWidth), Mth.r(outerHeight));
    super.size = SIZE;
  }

  private void initPos() {
    // 左側位置：PADDING + BORDER（與 outerWidth 的計算相符）
    POS = new Vector2(PADDING + BORDER, screenHeight() * HEIGHT_RATE + BORDER);
    super.pos = POS;
  }

  private void drawBoard() {
    RANGE = super.range;
    this.fill(Colors.TRANSPARENT);

    this.setColor(BORDER_COLOR);
    this.fillRoundedRect(0, 0, super.size.width(), super.size.height(), BORDER_RADIUS);

    int innerX = Mth.r(BORDER);
    int innerY = Mth.r(BORDER);
    int innerW = super.size.width() - Mth.r(BORDER * 2);
    int innerH = super.size.height() - Mth.r(BORDER * 2);

    this.setColor(INTERNAL_COLOR);
    this.fillRoundedRect(innerX, innerY, innerW, innerH, BORDER_RADIUS - Mth.r(BORDER));
    this.setColor(Color.BLACK);

    int totalGridHeight = Mth.r(innerHeight);
    for (int col = 0; col < Block.ROWS - 1; col++) {
      int gapX = Mth.r(BORDER + col * (tileSize + INTERNAL) + tileSize);
      int gapY = Mth.r(BORDER);
      this.fillRect(gapX, gapY, Mth.r(INTERNAL), totalGridHeight);
    }

    this.done();
  }

}
