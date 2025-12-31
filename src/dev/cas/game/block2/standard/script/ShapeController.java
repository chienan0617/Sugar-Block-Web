package dev.cas.game.block2.standard.script;

import com.badlogic.gdx.math.Vector2;
import com.cas.engine.core.ObjectManager;
import com.cas.engine.exception.ThrowError;
import com.cas.engine.graphic.Colors;
import com.cas.engine.object.GamePiece;
import com.cas.engine.utils.ScreenUtils;

import dev.cas.game.block2.standard.format.BoardPosition;
import dev.cas.game.block2.standard.format.ShapeInfo;
import dev.cas.game.block2.standard.format.ShapeType;
import dev.cas.game.block2.standard.object.Board;
import dev.cas.game.block2.standard.object.Shape;

public final class ShapeController extends GamePiece {
  public static final Shape[] shapes = new Shape[Shape.SHAPE_DISPLAY_NUM];
  public static final Vector2[] positions = new Vector2[Shape.SHAPE_DISPLAY_NUM];

  @Override
  public void init() {
    initPositions();
    newRound();
  }

  public static void remove(int index) {
    Shape shape = get(index); // * checked while get()
    ObjectManager.ensureEnd(shape);
    shapes[index] = null;
  }

  public static Shape get(int index) {
    checkOrThrow(index);
    return shapes[index];
  }

  public static void checkOrThrow(int index) {
    if (index >= shapes.length) {
      ThrowError.indexOutOfBounds(index);
    }
  }

  public static void initPositions() {
    int unitWidth = Board.WIDTH / Shape.SHAPE_DISPLAY_NUM;

    for (int i = 0; i < positions.length; i++) {
      int sy = (int) (ScreenUtils.getWidth() * 0.3F);
      int sx = (int) (unitWidth * (i + 0.5F)) + Board.PADDING;
      positions[i] = new Vector2(sx, sy);
    }
  }

  @SuppressWarnings("unused")
  public static void newRound() {
    final int MAX_ATTEMPTS = 300;
    Shape[] candidates = new Shape[shapes.length];
    boolean found = false;

    for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
      for (int i = 0; i < candidates.length; i++) {
        ShapeInfo data = new ShapeInfo(i, ShapeType.random(), Colors.random());
        candidates[i] = new Shape(null, data);
      }

      if (TileEmulator.canPlaceAnyOrderSequence(candidates)) {
        found = true;
        break;
      }
    }

    for (int i = 0; i < candidates.length; i++) {
      ObjectManager.ensureStart(candidates[i]);
      shapes[i] = candidates[i];
    }
  }

  public static void onShapeDragStop(Shape shape) {
    // clear any active glow highlights when drag stops
    GlowBorderController.clear();
    Vector2 visual = shape.getVisualPosition();

    if (true) {
      BoardPosition bestPos = GestureController.getBestPosition(shape, visual);

      if (bestPos == null) {
        shape.returnOriginalPlace();
        return;
      }

      if (BoardController.attemptToPlaceShape(shape, bestPos)) {
        remove(shape.info.index());
        StateManager.onPlaceDone();
        return;
      }
    }
    shape.returnOriginalPlace();
  }

  public static void onShapeDragging(Shape shape) {
    Vector2 logical = shape.getLogicalPosition();

    if (true) {
      BoardPosition bestPos = GestureController.getBestPosition(shape, logical);

      if (bestPos != null) {
        SnapshotController.tryShowSnapshot(shape, bestPos);
        GlowBorderController.tryShowGlowBorder(shape, bestPos);
      }
    }
  }

  @Override
  public void render() {
    for (int i = 0; i < shapes.length; i++) {
      if (shapes[i] != null) {
        shapes[i].render();
      }
    }
  }

  @Override
  public void update(float delta) {
    for (int i = 0; i < shapes.length; i++) {
      if (shapes[i] != null) {
        shapes[i].update(delta);
      }
    }
  }
}
