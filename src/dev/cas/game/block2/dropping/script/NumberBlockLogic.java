package dev.cas.game.block2.dropping.script;

import com.badlogic.gdx.math.Vector2;

import dev.cas.game.block2.dropping.format.BlockBoardPosition;
import dev.cas.game.block2.dropping.object.BlockBoard;
import dev.cas.game.block2.dropping.object.NumberBlock;

public final class NumberBlockLogic {
  public static Vector2 getAbsolutePosition(BlockBoardPosition pos) {
    final var p = BlockBoard.BORDER;
    final var i = BlockBoard.INTERNAL;

    float dx = BlockBoard.POS.x + p + pos.px() * (NumberBlock.SIZE + i) + i;
    float dy = BlockBoard.POS.y + p + pos.py() * (NumberBlock.SIZE + i) + i;

    return new Vector2(dx, dy);
  }
}
