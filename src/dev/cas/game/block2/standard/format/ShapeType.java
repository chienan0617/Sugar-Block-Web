package dev.cas.game.block2.standard.format;

import com.badlogic.gdx.math.Vector2;
import com.cas.engine.annotation.Nullable;
import com.cas.engine.utils.Mth;

public enum ShapeType {
  // 基本形狀（小，常出現）
  N_1x2(1, 2, 2, 10),
  N_2x1(2, 1, 2, 10),
  N_2x2(2, 2, 4, 10),

  // L 形（4格，常見）
  L_SHAPE_1_1(2, 3, 4, 3),
  L_SHAPE_2_1(2, 3, 4, 3),
  L_SHAPE_3_1(2, 3, 4, 3),
  L_SHAPE_4_1(2, 3, 4, 3),

  L_SHAPE_1_2(3, 2, 4, 3),
  L_SHAPE_2_2(3, 2, 4, 3),
  L_SHAPE_3_2(3, 2, 4, 3),
  L_SHAPE_4_2(3, 2, 4, 3),

  // T 形（4格，中等機率）
  T_SHAPE_1_1(3, 2, 4, 3),
  T_SHAPE_1_2(3, 2, 4, 3),
  T_SHAPE_2_1(2, 3, 4, 3),
  T_SHAPE_2_2(2, 3, 4, 3),

  // I 形（長條）
  I_4x1(4, 1, 4, 7),
  I_1x4(1, 4, 4, 7),

  // Z 形
  Z_SHAPE(3, 2, 4, 7),

  // O 方塊（3x3，比較稀有）
  O_3x3(3, 3, 9, 5),

  // 特殊形狀
  PLUS_3x3(3, 3, 5, 2),
  BIG_L_3x3_1(3, 3, 5, 2),
  BIG_L_3x3_2(3, 3, 5, 2),
  BIG_L_3x3_3(3, 3, 5, 2),
  BIG_L_3x3_4(3, 3, 5, 2),

  // B 類（稀有）
  B_2x2_1(2, 2, 2, 1),
  B_2x2_2(2, 2, 2, 1);

  public final int width;
  public final int height;
  public final int totalUnit;
  public final int chance; // 權重機率

  ShapeType(int width, int height, int totalUnit, int chance) {
    this.width = width;
    this.height = height;
    this.totalUnit = totalUnit;
    this.chance = chance;
  }

  public static ShapeType random() {
    int total = 0;
    for (ShapeType type : values()) {
      total += type.chance;
    }
    int r = Mth.nextInt(total);
    for (ShapeType type : values()) {
      r -= type.chance;
      if (r < 0) {
        return type;
      }
    }
    return N_2x2; // fallback（理論上不會發生）
  }

  public Vector2 getSize() {
    return new Vector2(this.width, this.height);
  }

  @Nullable
  public int[][] getData() {
    return switch (this) {
      // case N_1x1 -> new int[][]{
      // {1}
      // };
      case N_1x2 -> new int[][] {
          { 1 },
          { 1 }
      };
      case N_2x1 -> new int[][] {
          { 1, 1 }
      };
      case N_2x2 -> new int[][] {
          { 1, 1 },
          { 1, 1 }
      };
      case L_SHAPE_1_1 -> new int[][] {
          { 1, 0 },
          { 1, 0 },
          { 1, 1 }
      };
      case L_SHAPE_2_1 -> new int[][] {
          { 1, 1 },
          { 1, 0 },
          { 1, 0 }
      };
      case L_SHAPE_3_1 -> new int[][] {
          { 1, 1 },
          { 0, 1 },
          { 0, 1 },
      };
      case L_SHAPE_4_1 -> new int[][] {
          { 0, 1 },
          { 0, 1 },
          { 1, 1 },
      };
      case L_SHAPE_1_2 -> new int[][] {
          { 1, 1, 1 },
          { 0, 0, 1 },
      };
      case L_SHAPE_2_2 -> new int[][] {
          { 1, 1, 1 },
          { 1, 0, 0 },
      };
      case L_SHAPE_3_2 -> new int[][] {
          { 0, 0, 1 },
          { 1, 1, 1 },
      };
      case L_SHAPE_4_2 -> new int[][] {
          { 1, 0, 0 },
          { 1, 1, 1 },
      };
      case T_SHAPE_1_1 -> new int[][] {
          { 1, 1, 1 },
          { 0, 1, 0 }
      };
      case T_SHAPE_1_2 -> new int[][] {
          { 0, 1, 0 },
          { 1, 1, 1 }
      };
      case T_SHAPE_2_1 -> new int[][] {
          { 0, 1 },
          { 1, 1 },
          { 0, 1 },
      };
      case T_SHAPE_2_2 -> new int[][] {
          { 1, 0 },
          { 1, 1 },
          { 1, 0 },
      };
      case I_4x1 -> new int[][] {
          { 1, 1, 1, 1 }
      };
      case I_1x4 -> new int[][] {
          { 1 },
          { 1 },
          { 1 },
          { 1 }
      };
      case Z_SHAPE -> new int[][] {
          { 1, 1, 0 },
          { 0, 1, 1 }
      };
      case PLUS_3x3 -> new int[][] {
          { 0, 1, 0 },
          { 1, 1, 1 },
          { 0, 1, 0 }
      };
      case BIG_L_3x3_1 -> new int[][] {
          { 1, 0, 0 },
          { 1, 0, 0 },
          { 1, 1, 1 }
      };
      case BIG_L_3x3_2 -> new int[][] {
          { 1, 1, 1 },
          { 1, 0, 0 },
          { 1, 0, 0 }
      };
      case BIG_L_3x3_3 -> new int[][] {
          { 0, 0, 1 },
          { 0, 0, 1 },
          { 1, 1, 1 }
      };
      case BIG_L_3x3_4 -> new int[][] {
          { 1, 1, 1 },
          { 1, 0, 0 },
          { 1, 0, 0 }
      };
      case O_3x3 -> new int[][] {
          { 1, 1, 1 },
          { 1, 1, 1 },
          { 1, 1, 1 }
      };
      case B_2x2_1 -> new int[][] {
          { 0, 1 },
          { 1, 0 },
      };
      case B_2x2_2 -> new int[][] {
          { 1, 0 },
          { 0, 1 },
      };

      default -> throw new IllegalStateException("can't not handle type: " + this);
    };
  }

  public int getTotalUnit() {
    return this.totalUnit;
  }
}
