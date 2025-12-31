package com.cas.engine.animation;

import com.badlogic.gdx.math.MathUtils;
import com.cas.engine.model.Camera2D;
import com.cas.engine.object.GameObject;


public class ShockAnimation extends GameObject implements Animation<ShockAnimation> {
  private final float duration; // 持續時間 (秒)
  private final float amplitude; // 最大水平位移 (像素)
  private final float frequency; // 頻率 (Hz) -> 控制左右抽動速度

  private float elapsed;
  private boolean playing;
  private float originX, originY;
  private float rotationAmplitude; // optional: 最大旋轉角度 (度)

  /**
   * frequency: 建議 8~30 Hz 範圍。越高越快（抽動越快）。
   * rotationAmp: 若不想要轉角效果，傳 0。
   */
  public ShockAnimation(String name, float duration, float amplitude, float frequency,
      float rotationAmp) {
    super(name);
    this.duration = duration;
    this.amplitude = amplitude;
    this.frequency = frequency;
    this.rotationAmplitude = rotationAmp;
  }

  @Override
  public ShockAnimation start() {
    if (Camera2D.camera != null) {
      originX = Camera2D.camera.position.x;
      originY = Camera2D.camera.position.y;
    }
    elapsed = 0f;
    playing = true;
    return this;
  }

  @Override
  public ShockAnimation stop() {
    playing = false;
    elapsed = 0f;
    // 回復原位與角度
    if (Camera2D.camera != null) {
      Camera2D.camera.position.x = originX;
      Camera2D.camera.position.y = originY;
      Camera2D.camera.up.set(0, 1, 0); // 恢復朝向（通常不需要，但保險）
      // 如果你用 camera.rotate(...) 做過旋轉，這裡可能要 reset 矩陣；此處先把 rotation 歸零
      Camera2D.camera.update();
    }
    return this;
  }

  @Override
  public void update(float delta) {
    if (!playing || Camera2D.camera == null)
      return;

    elapsed += delta;

    if (elapsed >= duration) {
      stop();
      return;
    }

    // progress 0..1，使用二次或指數包絡使收尾更平滑
    float progress = elapsed / duration;
    float envelope = 1f - progress; // 線性衰減
    envelope = envelope * envelope; // 平滑一點 (二次衰減)

    // 正弦振盪 (左右)
    float angle = elapsed * frequency * MathUtils.PI2; // elapsed*freq*2π
    float offsetX = MathUtils.sin(angle) * amplitude * envelope;

    // 小的垂直短促位移，讓感覺不那麼死板（可調）
    float offsetY = MathUtils.sin(angle * 1.5f) * (amplitude * 0.12f) * envelope;

    // 可選微小隨機化（讓每次有一點不同）
    float jitter = MathUtils.random(-0.06f, 0.06f) * amplitude * envelope;
    offsetX += jitter;

    // 直接設定回原始位置 + 偏移（避免累加）
    Camera2D.camera.position.x = originX + offsetX;
    Camera2D.camera.position.y = originY + offsetY;

    // 可選：加入微小轉角（看起來更有力）
    if (rotationAmplitude != 0f) {
      // float rotDeg = MathUtils.sin(angle) * rotationAmplitude * envelope;
      // libGDX camera 沒有直接角度屬性——可以改 transform 或者只在渲染時用 shader。
      // 這裡僅示意：若你有方法旋轉攝影機，請在這裡套用 rotDeg。
    }

    Camera2D.camera.update();
  }
}
