package dev.cas.game.block2.dropping.script;

import com.cas.engine.core.ObjectManager;
import com.cas.engine.exception.ThrowError;
import com.cas.engine.object.GamePiece;
import com.cas.engine.object.ObjectLifeCycle;

import dev.cas.game.block2.dropping.format.BlockBoardPosition;
import dev.cas.game.block2.dropping.object.NumberBlock;

public final class NumberBlockState extends GamePiece {
  public static final NumberBlock[][] blocks = new NumberBlock[NumberBlock.COLS][NumberBlock.ROWS];

  public static void put(NumberBlock block) {
    ThrowError.checkOrThrow(block);
    ObjectManager.ensureStart(block);
    BlockBoardPosition pos = block.data.pos();
    blocks[pos.px()][pos.py()] = block;
  }

  public static NumberBlock get(BlockBoardPosition pos) {
    ThrowError.checkOrThrow(pos);
    return blocks[pos.px()][pos.py()];
  }

  public static void remove(BlockBoardPosition pos) {
    ThrowError.checkOrThrow(pos);
    ObjectManager.ensureEnd(get(pos));
    blocks[pos.px()][pos.py()] = null;
  }

  @Override
  public void update(float delta) {
    ObjectLifeCycle.updateObjects(blocks, delta);
  }

  @Override
  public void render() {
    ObjectLifeCycle.renderObjects(blocks);
  }
}
