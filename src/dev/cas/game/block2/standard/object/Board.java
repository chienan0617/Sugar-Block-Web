package dev.cas.game.block2.standard.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.cas.engine.component.Canvas;
import com.cas.engine.format.Size;
import com.cas.engine.graphic.Colors;

public final class Board extends Canvas {
  public static final int PADDING = Math.round(screenWidth() * 0.0375F);
  public static final int BOARD_RADIUS = 25;
  public static final int INTERNAL = 1;
  public static final int BORDER = 15;
  public static final Vector2 START_POINT = new Vector2(
      PADDING + BORDER, screenHeight() * 0.4F);
  public static final int WIDTH = screenWidth() - (PADDING + BORDER) * 2;
  public static final int HEIGHT = WIDTH;

  public static int[] RANGE;
  public static Vector2 POS;
  public static Size SIZE;

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
    super.size = new Size(WIDTH, HEIGHT);
    SIZE = super.size;
  }

  private void initPos() {
    super.pos = new Vector2(START_POINT.x, START_POINT.y);
    POS = super.pos;
  }

  private void drawBoard() {
    RANGE = super.range;

    Color themeColor = Colors.B2;
    Color slotColor = Colors.B1;

    this.fill(Colors.TRANSPARENT);
    this.setColor(themeColor);
    this.fillRoundedRect(0, 0, super.size.width(), super.size.height(), BOARD_RADIUS);

    int availableWidth = this.size.width() - (BORDER * 2);
    int totalGapWidth = (Tile.COLS - 1) * INTERNAL;
    int tileSize = (availableWidth - totalGapWidth) / Tile.COLS;

    // 先畫每個插槽（格子）
    this.setColor(slotColor);
    for (int row = 0; row < Tile.ROWS; row++) {
      for (int col = 0; col < Tile.COLS; col++) {
        int x = BORDER + col * (tileSize + INTERNAL);
        int y = BORDER + row * (tileSize + INTERNAL);
        this.fillRect(x, y, tileSize, tileSize);
      }
    }

    // 再畫內部的間隙（INTERNAL）為黑色
    this.setColor(Color.BLACK);

    int totalGridWidth = tileSize * Tile.COLS + INTERNAL * (Tile.COLS - 1);
    int totalGridHeight = tileSize * Tile.ROWS + INTERNAL * (Tile.ROWS - 1);

    // 垂直間隙
    for (int col = 0; col < Tile.COLS - 1; col++) {
      int gapX = BORDER + col * (tileSize + INTERNAL) + tileSize; // gap 開始 x
      int gapY = BORDER;
      this.fillRect(gapX, gapY, INTERNAL, totalGridHeight);
    }

    // 水平間隙
    for (int row = 0; row < Tile.ROWS - 1; row++) {
      int gapY = BORDER + row * (tileSize + INTERNAL) + tileSize; // gap 開始 y
      int gapX = BORDER;
      this.fillRect(gapX, gapY, totalGridWidth, INTERNAL);
    }

    this.done();
  }
}
