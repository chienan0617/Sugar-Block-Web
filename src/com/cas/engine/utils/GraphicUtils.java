package com.cas.engine.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.cas.engine.GameInstance;
import com.cas.engine.annotation.Nullable;


/**
 * NOTE:
 * - 這個工具把「Pixmap 的縮放」與「Texture 的建立」明確分離。
 * - adjustPixmapSize: 只處理 Pixmap -> 回傳新的 Pixmap，**不會 dispose 傳入的 pixmap**。
 * - createTextureFromPixmap: 在 GL thread 上建立 Texture，建立完可以 dispose pixmap。
 * - 不再使用 texture.getTextureData().consumePixmap()，避免所有權混亂。
 */
public final class GraphicUtils {

  /**
   * 將一個 Pixmap 縮放（不會 dispose 原 pixmap），回傳一個新的 Pixmap。
   * 呼叫端負責呼叫返回 pixmap 的 dispose()。
   */
  public static Pixmap adjustPixmapSize(int width, int height, Pixmap src) {
    if (src == null)
      return null;
    Pixmap resized = new Pixmap(width, height, src.getFormat());
    resized.setFilter(Pixmap.Filter.BiLinear);
    resized.drawPixmap(src, 0, 0, src.getWidth(), src.getHeight(),
        0, 0, resized.getWidth(), resized.getHeight());
    return resized;
  }

  /**
   * 載入檔案並縮放成新的 Pixmap（呼叫端負責 dispose 新的 pixmap）。
   * 這個方法只在 CPU（任意 thread）執行 Pixmap 操作。
   */
  @Nullable
  public static Pixmap loadAndAdjustPixmap(String internalPath, int width, int height) {
    if (internalPath == null)
      return null;
    FileHandle fh = Gdx.files.internal(internalPath);
    Pixmap src = new Pixmap(fh);
    Pixmap resized = adjustPixmapSize(width, height, src);
    src.dispose();
    return resized;
  }

  /**
   * 在 GL thread 上建立 Texture 並回傳。建立完可以安全地 dispose pixmap（Texture 會把像素上傳到 GPU）。
   * 必須在 GL thread 呼叫（如果不確定，使用 Gdx.app.postRunnable(...) 包起來）。
   */
  public static Texture createTextureFromPixmap(Pixmap pixmap) {
    if (pixmap == null)
      return null;
    Texture t = new Texture(pixmap);
    return t;
  }

  /**
   * **危險 / 不建議的方法已移除**：
   * - 不再提供 adjustTextureSize(width,height, Texture) 會消耗 texture 的方法。
   * - 若真的需要從一個 Texture 取得 Pixmap，需要確定該 texture 的 TextureData 支援 consumePixmap()，
   * 並且要非常小心所有權/dispose；最好避免此行為。
   */

  @Nullable
  public static Pixmap tintPixmap(Pixmap ori, Color tc) {
    if (ori == null || tc == null) {
      return null;
    }
    Pixmap newPixmap = new Pixmap(ori.getWidth(), ori.getHeight(),
        ori.getFormat());
    Color pc = new Color();
    Color fc = new Color();

    for (int y = 0; y < ori.getHeight(); y++) {
      for (int x = 0; x < ori.getWidth(); x++) {
        Color.rgba8888ToColor(pc, ori.getPixel(x, y));
        if (pc.a > 0) {
          float temp = 1.25F;
          float lum = (pc.r * 0.299f + pc.g * 0.587f + pc.b * 0.114f) * temp;
          fc.set(tc.r * lum, tc.g * lum, tc.b * lum, pc.a);

          newPixmap.setColor(fc);
          newPixmap.drawPixel(x, y);
        }
      }
    }
    return newPixmap;
  }

  public static void setBatchToWhite() {
    GameInstance.objectBatch.setColor(Color.WHITE);
  }
}
