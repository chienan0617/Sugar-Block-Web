package com.cas.engine.graphic;

import com.badlogic.gdx.graphics.Color;
import com.cas.engine.utils.ColorUtils;
import com.cas.engine.utils.Mth;

public class Colors {

  public static final Color[] colors = new Color[] {
      ColorUtils.hex("#F3EAC6"), // 曙光米 Daybreak
      ColorUtils.hex("#F6F1D3"), // 淡奶油 Pale Cream
      ColorUtils.hex("#DFDAD8"), // 被動灰 Passive
      ColorUtils.hex("#FFF2EF"), // 柔和桃 Soft Peach
      ColorUtils.hex("#FAD6D6"), // 嬰兒粉 Baby Pink
      ColorUtils.hex("#FFE2CC"), // 杏仁色 Apricot
      ColorUtils.hex("#FFDAC1"), // 淺杏橘 Light Apricot
      ColorUtils.hex("#FCE1E4"), // 粉香檳 Pink Champagne
      ColorUtils.hex("#F6CED8"), // 玫瑰石 Rose Quartz
      ColorUtils.hex("#F1C6D4"), // 腮紅粉 Blush Pink
      ColorUtils.hex("#E6C0B3"), // 粉黏土 Blush Clay
      ColorUtils.hex("#D8BCC5"), // 粉石英 Carnelian
      ColorUtils.hex("#D2B1A3"), // 溫暖褐 Warm Taupe
      ColorUtils.hex("#C5B0A0"), // 曬樹褐 Tanbark
      ColorUtils.hex("#B6A29A"), // 石灰輝 Stone Gray
      ColorUtils.hex("#DECBAA"), // 杏仁牛奶 Almond Milk
      ColorUtils.hex("#D3B88C"), // 砂岩 Sandstone
      ColorUtils.hex("#E0B75E"), // 晨光金 Sundew
      ColorUtils.hex("#D9A24E"), // 稻草黃 Straw Harvest
      ColorUtils.hex("#A16B47"), // 古銅棕 Burnt Sienna
      ColorUtils.hex("#B17B5C"), // 粘土棕 Clay Brown
      ColorUtils.hex("#8F5A3C"), // 摩卡 Mocha
      ColorUtils.hex("#6C3E2A"), // 咖啡豆 Coffee Bean
      ColorUtils.hex("#554C4A"), // 深棕褐 Dark Auburn
      ColorUtils.hex("#A82E33"), // 熱情紅 Heartthrob
      ColorUtils.hex("#FF6F61"), // 柔珊瑚 Muted Coral
      ColorUtils.hex("#A9B8D3"), // 天際藍 Skyline
      ColorUtils.hex("#C1E5F2"), // 法國海岸 French Coast
      ColorUtils.hex("#B0D0D3"), // 粉藍 Powder Blue
      ColorUtils.hex("#A1C6C8"), // 海風藍 Ocean Breeze
      ColorUtils.hex("#B2DFE4"), // 湖水藍 Aqua Tone
      ColorUtils.hex("#A2D8D0"), // 海泡綠 Seafoam
      ColorUtils.hex("#ACCFCB"), // 微風綠 Breeze Green
      ColorUtils.hex("#CDEDEA"), // 冰薄荷 Ice Mint
      ColorUtils.hex("#AED9E0"), // 薄霧藍 Mist Blue
      ColorUtils.hex("#93A8AC"), // 灰藍 Dusty Blue
      ColorUtils.hex("#3D4D5C"), // 工業藍 Industrial Blue
      ColorUtils.hex("#5B6C7D"), // 灰藍灰 Dust Blue
      ColorUtils.hex("#3A5A8A"), // 升藍 Upward
      ColorUtils.hex("#1E90FF"), // 道奇藍 Dodger Blue
      ColorUtils.hex("#00BFFF"), // 深天藍 Deep Sky Blue
      ColorUtils.hex("#D6D6D6"), // 淺水泥 Light Cement
      ColorUtils.hex("#C0C0C0"), // 銀霧 Silver Mist
      ColorUtils.hex("#A0A0A0"), // 陶土灰 Studio Clay
      ColorUtils.hex("#A8A8A8"), // 晨霧灰 Foggy Morning
      ColorUtils.hex("#B4B8B1"), // 鼠尾草綠 Sage Green
      ColorUtils.hex("#A3B1A8"), // 苔蘚灰 Moss Gray
      ColorUtils.hex("#7E9485"), // 橄欖綠 Olive Green
      ColorUtils.hex("#597B71"), // 深苔綠 Deep Moss
      ColorUtils.hex("#449F7F"), // 森林綠 Forest Green
      ColorUtils.hex("#66CDAA"), // 中水綠 Aquamarine
      ColorUtils.hex("#83C8B0"), // 水波綠 Watery
      ColorUtils.hex("#A8D5BA"), // 霜葉綠 Frosted Leaf
      ColorUtils.hex("#D1E2B8"), // 晨霧綠 Dawn Mist
      ColorUtils.hex("#D4E8B3"), // 芹菜綠 Celery
      ColorUtils.hex("#BCCCE0"), // 淡薰衣草 Mist Lavender
      ColorUtils.hex("#EADCF6"), // 淡紫 Lilac Light
      ColorUtils.hex("#C9C2E1"), // 淺薰衣草 Lite Lavender
      ColorUtils.hex("#BFA2CC"), // 欣悅紫 Euphoric Lilac
      ColorUtils.hex("#D8B7DD"), // 迷霧紫 Lilac Haze
      ColorUtils.hex("#6A4C8C"), // 深梅紫 Deep Plum
      ColorUtils.hex("#8C8E9F"), // 暮色藍 Twilight Blue
      ColorUtils.hex("#4B3F3F") // 木炭 Charcoal
  };

  public static final Color DAY_BREAK = colors[0];
  public static final Color PALE_CREAM = colors[1];
  public static final Color PASSIVE = colors[2];
  public static final Color SOFT_PEACH = colors[3];
  public static final Color BABY_PINK = colors[4];
  public static final Color APRICOT = colors[5];
  public static final Color LIGHT_APRICOT = colors[6];
  public static final Color PINK_CHAMPAGNE = colors[7];
  public static final Color ROSE_QUARTZ = colors[8];
  public static final Color BLUSH_PINK = colors[9];
  public static final Color BLUSH_CLAY = colors[10];
  public static final Color CARNELIAN = colors[11];
  public static final Color WARM_TAUPE = colors[12];
  public static final Color TANBARK = colors[13];
  public static final Color STONE_GRAY = colors[14];
  public static final Color ALMOND_MILK = colors[15];
  public static final Color SANDSTONE = colors[16];
  public static final Color SUNDEW = colors[17];
  public static final Color STRAW_HARVEST = colors[18];
  public static final Color BURNT_SIENNA = colors[19];
  public static final Color CLAY_BROWN = colors[20];
  public static final Color MOCHA = colors[21];
  public static final Color COFFEE_BEAN = colors[22];
  public static final Color DARK_AUBURN = colors[23];
  public static final Color HEARTTHROB = colors[24];
  public static final Color MUTED_CORAL = colors[25];
  public static final Color SKYLINE = colors[26];
  public static final Color FRENCH_COAST = colors[27];
  public static final Color POWDER_BLUE = colors[28];
  public static final Color OCEAN_BREEZE = colors[29];
  public static final Color AQUA_TONE = colors[30];
  public static final Color SEAFOAM = colors[31];
  public static final Color BREEZE_GREEN = colors[32];
  public static final Color ICE_MINT = colors[33];
  public static final Color MIST_BLUE = colors[34];
  public static final Color DUSTY_BLUE = colors[35];
  public static final Color INDUSTRIAL_BLUE = colors[36];
  public static final Color DUST_BLUE = colors[37];
  public static final Color UPWARD = colors[38];
  public static final Color DODGER_BLUE = colors[39];
  public static final Color DEEP_SKY_BLUE = colors[40];
  public static final Color LIGHT_CEMENT = colors[41];
  public static final Color SILVER_MIST = colors[42];
  public static final Color STUDIO_CLAY = colors[43];
  public static final Color FOGGY_MORNING = colors[44];
  public static final Color SAGE_GREEN = colors[45];
  public static final Color MOSS_GRAY = colors[46];
  public static final Color OLIVE_GREEN = colors[47];
  public static final Color DEEP_MOSS = colors[48];
  public static final Color FOREST_GREEN = colors[49];
  public static final Color AQUAMARINE = colors[50];
  public static final Color WATERY = colors[51];
  public static final Color FROSTED_LEAF = colors[52];
  public static final Color DAWN_MIST = colors[53];
  public static final Color CELERY = colors[54];
  public static final Color MIST_LAVENDER = colors[55];
  public static final Color LILAC_LIGHT = colors[56];
  public static final Color LITE_LAVENDER = colors[57];
  public static final Color EUPHORIC_LILAC = colors[58];
  public static final Color LILAC_HAZE = colors[59];
  public static final Color DEEP_PLUM = colors[60];
  public static final Color TWILIGHT_BLUE = colors[61];
  public static final Color CHARCOAL = colors[62];

  public static final Color TRANSPARENT = new Color(1F, 1F, 1F, 0F);

  public static final Color C1 = new Color(144F / 256F, 196F / 256F, 244F / 256F, 1F);
  public static final Color C2 = new Color(210F / 255F, 89F / 255F, 112F / 255F, 1F);
  public static final Color C3 = new Color(228F / 255F, 113F / 255F, 80F / 255F, 1F);
  public static final Color C4 = new Color(171F / 255F, 135F / 255F, 207F / 255F, 1F);
  public static final Color C5 = new Color(244F / 255F, 169F / 255F, 214F / 255F, 1F);
  public static final Color B1 = new Color(56F / 255F, 32F / 255F, 47F / 255F, 1F);
  public static final Color B2 = new Color(80F / 255F, 51F / 255F, 64F / 255F, 1F);
  public static final Color B3 = new Color(98F / 255F, 65F / 255F, 80F / 255F, 1F);

  public static final Color[] TILE_COLORS = new Color[] {
    C1, C2, C3, C4, C5
  };

  public static Color random() {
    return TILE_COLORS[Mth.nextInt(TILE_COLORS.length)];
    // return new Color(r, g, b, 1);
  }

  @Deprecated
  public static Color randomGreen() {
    Color[] greens = new Color[] {
        AQUAMARINE, WATERY, FROSTED_LEAF, DAWN_MIST, CELERY,
        SAGE_GREEN, MOSS_GRAY, OLIVE_GREEN, DEEP_MOSS, FOREST_GREEN
    };
    return greens[Mth.nextInt(greens.length)];
  }

  public static Color randomExcept(Color... excepts) {
    Color color;
    boolean found;
    do {
      found = false;
      color = random();
      for (Color ex : excepts) {
        if (color.equals(ex)) {
          found = true;
          break;
        }
      }
    } while (found);
    return color;
  }
}
