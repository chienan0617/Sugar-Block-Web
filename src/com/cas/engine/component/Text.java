package com.cas.engine.component;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.cas.engine.GameInstance;
import com.cas.engine.format.Size;
import com.cas.engine.format.TextAlign;

/**
 * Text component — **全部使用 FreeType**（gdx-freetype）產生 BitmapFont。
 *
 * - 若指定的 ttf 檔案或 FreeType 不可用，會回退到預設 BitmapFont()（並在 log 中警告）。
 * - 建議在 assets/fonts/ 放置字型檔，並以相對路徑傳入（例如 "fonts/default.ttf"）。
 */
public class Text extends Sized {

  private static final Map<String, BitmapFont> fontCache = new HashMap<>();

  private BitmapFont font;
  private GlyphLayout layout = new GlyphLayout();
  private String text = "";
  private TextAlign align = TextAlign.ALIGN_LEFT;
  private boolean wrap = false;
  private Color color = Color.WHITE.cpy();
  private float scale = 1F;

  // 用於辨識是否由 FreeType 生成（cache key）
  @SuppressWarnings("unused")
  private String ttfPath = null;
  @SuppressWarnings("unused")
  private int ttfSizePx = 0;

  private static final String DEFAULT_TTF = "fonts/SpaceGrotesk-VariableFont_wght.ttf";
  private static final int DEFAULT_TTF_SIZE = 24;

  // ---------- constructors ----------
  /**
   * 使用預設 ttf（fonts/default.ttf）與預設大小（24px）。
   * 若找不到或 FreeType 無法使用，會回退到 BitmapFont()。
   */
  public Text(String name, Vector2 pos) {
    super(name, Size.ZERO, pos);
    initFromTtf(DEFAULT_TTF, DEFAULT_TTF_SIZE);
  }

  /**
   * 指定 ttf 路徑與像素大小（pixel size）。
   * 範例: new Text("label", new Vector2(10,10), "fonts/NotoSansTC-Regular.ttf",
   * 32);
   */
  public Text(String name, Vector2 pos, String fontFilePath, int fontSizePx) {
    super(name, Size.ZERO, pos);
    if (fontFilePath == null || fontFilePath.isEmpty() || fontSizePx <= 0) {
      initFromTtf(DEFAULT_TTF, DEFAULT_TTF_SIZE);
    } else {
      initFromTtf(fontFilePath, fontSizePx);
    }
  }

  // ---------- init ----------
  private void initFromTtf(String fontFilePath, int fontSizePx) {
    this.ttfPath = fontFilePath;
    this.ttfSizePx = fontSizePx;

    String key = cacheKey(fontFilePath, fontSizePx);
    BitmapFont cached = fontCache.get(key);
    if (cached != null) {
      this.font = cached;
      initDefaults();
      return;
    }

    try {
      FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal(fontFilePath));
      FreeTypeFontGenerator.FreeTypeFontParameter p = new FreeTypeFontGenerator.FreeTypeFontParameter();
      p.size = fontSizePx;
      p.incremental = true; // 建議
      // 你可以自訂需要的字元集：p.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "中文..."
      p.minFilter = TextureFilter.Linear;
      p.magFilter = TextureFilter.Linear;

      BitmapFont generated = gen.generateFont(p);
      gen.dispose();

      if (generated != null && generated.getRegion() != null && generated.getRegion().getTexture() != null) {
        generated.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
      }

      fontCache.put(key, generated);
      this.font = generated;
    } catch (Throwable t) {
      Gdx.app.error("Text", "FreeType load failed for '" + fontFilePath + "': " + t.getMessage());
      // fallback to default BitmapFont
      this.font = new BitmapFont();
    }

    initDefaults();
  }

  private static String cacheKey(String path, int size) {
    return path + "@" + size;
  }

  private void initDefaults() {
    if (font != null) {
      font.getData().setScale(scale);
      font.setColor(color);
    }
  }

  // ---------- setters / getters ----------
  public Text setText(String t) {
    this.text = t == null ? "" : t;
    return this;
  }

  public String getText() {
    return this.text;
  }

  public Text setColor(Color c) {
    if (c != null) {
      this.color = c.cpy();
      if (font != null)
        font.setColor(this.color);
    }
    return this;
  }

  /**
   * 若要改變尺寸，盡量使用不同 fontSizePx 生成不同 font，而不要大量依賴 setScale 放大位圖。
   * setScale 仍可作微調（0.5~2x 情況），但過度放大仍會失真。
   */
  public Text setScale(float s) {
    if (s <= 0)
      s = 1f;
    this.scale = s;
    if (font != null)
      font.getData().setScale(scale);
    return this;
  }

  public Text setAlign(TextAlign a) {
    if (a == TextAlign.ALIGN_LEFT || a == TextAlign.ALIGN_CENTER || a == TextAlign.ALIGN_RIGHT) {
      this.align = a;
    }
    return this;
  }

  public Text setWrap(boolean wrap) {
    this.wrap = wrap;
    return this;
  }

  public Text setPos(Vector2 pos) {
    this.pos = pos;
    return this;
  }

  public void setVisible(boolean v) {
    this.visible = v;
  }

  // ---------- measurement ----------
  public float getTextWidth() {
    if (font == null || text == null || text.isEmpty())
      return 0f;
    if (wrap) {
      float target = size.width();
      layout.setText(font, text, color, target, Align.left, true);
    } else {
      layout.setText(font, text);
    }
    return layout.width;
  }

  public float getTextHeight() {
    if (font == null || text == null || text.isEmpty())
      return 0f;
    if (wrap) {
      float target = size.width();
      layout.setText(font, text, color, target, Align.left, true);
    } else {
      layout.setText(font, text);
    }
    return layout.height;
  }

  // ---------- rendering ----------
  @Override
  public boolean isScreenSpace() {
    return true;
  }

  @Override
  public void render() {
    if (!visible || font == null || text == null || text.isEmpty())
      return;

    float drawX = pos.x;
    float drawY = pos.y + size.height();

    if (wrap) {
      float targetWidth = size.width();
      int gAlign = Align.left;
      if (align == TextAlign.ALIGN_CENTER)
        gAlign = Align.center;
      else if (align == TextAlign.ALIGN_RIGHT)
        gAlign = Align.right;

      layout.setText(font, text, color, targetWidth, gAlign, true);
      drawX = pos.x;
    } else {
      layout.setText(font, text);
      float innerWidth = layout.width;
      if (align == TextAlign.ALIGN_CENTER) {
        drawX = pos.x + (size.width() - innerWidth) / 2f;
      } else if (align == TextAlign.ALIGN_RIGHT) {
        drawX = pos.x + (size.width() - innerWidth);
      } else {
        drawX = pos.x;
      }
    }

    // 不在此處 begin()/end() uiBatch，沿用你的渲染流程
    font.draw(GameInstance.objectBatch, layout, drawX, drawY);
  }

  @Override
  public void dispose() {
    // 不自動 dispose 快取的 font；若希望在場景結束清除全部快取，呼叫 disposeAllCachedFonts()
    font = null;
  }

  /** 全域釋放所有快取的字體（在遊戲結束或切換場景時呼叫） */
  public static void disposeAllCachedFonts() {
    for (BitmapFont f : fontCache.values()) {
      try {
        f.dispose();
      } catch (Throwable t) {
        // ignore
      }
    }
    fontCache.clear();
  }
}
