package dev.cas.game.block2.standard;

import com.cas.engine.component.Background;
import com.cas.engine.component.Scene;
import com.cas.engine.graphic.Colors;
import com.cas.engine.object.GamePiece;

import dev.cas.game.block2.standard.object.Board;
import dev.cas.game.block2.standard.script.GlowBorderController;
import dev.cas.game.block2.standard.script.ShapeController;
import dev.cas.game.block2.standard.script.TileController;

public class StandardScene extends Scene {

  public StandardScene(String name, String sceneName) {
    super(name, sceneName, new GamePiece[] {
        // new Canvas(null, new Size(50, 50), new Vector2(50,
        // 50)).fill(Colors.APRICOT).done(),
        new Background(null, Colors.B3),
        new Board(null),
        new TileController(),
        new ShapeController(),
        // new GlowBorder(null, new GlowingBorderInfo(MatrixType.COLS, 3, Colors.OLIVE_GREEN)),
        new GlowBorderController(),
    });
  }
}
