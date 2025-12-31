package dev.cas.game.block2.dropping;

import com.badlogic.gdx.graphics.Color;
import com.cas.engine.component.Background;
import com.cas.engine.component.Scene;
import com.cas.engine.graphic.Colors;
import com.cas.engine.object.GamePiece;

import dev.cas.game.block2.dropping.format.BlockBoardPosition;
import dev.cas.game.block2.dropping.format.NumberBlockData;
import dev.cas.game.block2.dropping.object.BlockBoard;
import dev.cas.game.block2.dropping.object.NumberBlock;
import dev.cas.game.block2.dropping.script.NumberBlockState;

public class DroppingScene extends Scene {

  public DroppingScene(String name, String sceneName) {
    super(name, sceneName, new GamePiece[0]);
    add(new Background(null, new Color(21 / 255F, 25 / 255F, 28 / 255F, 1F)));
    add(new BlockBoard(null));
    NumberBlockState.put(new NumberBlock(new NumberBlockData(2, Colors.random(), new BlockBoardPosition(2, 1))));

    add(new NumberBlockState());
  }

}
