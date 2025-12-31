package com.cas.engine.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.cas.engine.action.GestureBase;
import com.cas.engine.format.Size;
import com.cas.engine.utils.InputUtils;

public class Draggable extends Drawable implements GestureBase {
  public boolean isDragging = false;
  public boolean canDrag = true;
  public Vector2 grabOffset = new Vector2(); // mouse -> object offset when started isDragging

  // 視覺偏移（在拖曳時讓物件看起來高出實際位置）。
  public static final float DRAG_VISUAL_Y_OFFSET = Gdx.graphics.getHeight() * 0.1F;

  // 誇張化參數（水平與垂直）
  public static final float X_EXAGGERATION = 0.5f;
  public static final float Y_EXAGGERATION = 0.5f; // <-- 新增的垂直誇張參數（可調）
  public static final float MAX_EXTRA_OFFSET = 200f;
  public static final float MAX_EXTRA_Y = 150f; // 垂直最大誇張量（可調）

  private float dragStartX = 0f;
  private float lastExtraX = 0f; // 用於停止時還原

  private float dragStartY = 0f;
  private float lastExtraY = 0f; // 垂直誇張的暫存

  public Draggable(String name, Size size, Vector2 pos) {
    super(name, size, pos);
  }

  public Draggable(String name) {
    super(name);
  }

  @Override
  public void init() {
    super.init();
    super.initRange();
  }

  @Override
  public void update(float delta) {
    super.update(delta);
    onDrag();
  }

  @Override
  public void onDrag() {
    if (!canDrag)
      return;

    int[] mouse = InputUtils.getMousePosition();
    if (mouse == null)
      return;

    int mouseX = mouse[0];
    int mouseY = mouse[1];
    int screenH = Gdx.graphics.getHeight();

    // Start isDragging when mouse just touched inside range
    if (!isDragging && InputUtils.isJustTouchedInRange(super.range)) {
      isDragging = true;
      float worldMouseY = screenH - mouseY;
      grabOffset.set(mouseX - this.pos.x, worldMouseY - this.pos.y);

      // 記下拖曳起點 (logical)
      dragStartX = this.pos.x;
      lastExtraX = 0f;

      dragStartY = this.pos.y;
      lastExtraY = 0f;
    }

    // While isDragging and mouse is touched -> update position so that mouse keeps
    // the grab offset
    if (isDragging && InputUtils.isTouched()) {
      float worldMouseY = screenH - mouseY;
      float newX = mouseX - grabOffset.x; // 這是「邏輯位置」(without exaggeration)
      float newY = worldMouseY - grabOffset.y;

      // 計算水平位移並誇張化
      float deltaX = newX - dragStartX;
      float extraX = MathUtils.clamp(deltaX * X_EXAGGERATION, -MAX_EXTRA_OFFSET, MAX_EXTRA_OFFSET);
      lastExtraX = extraX;

      // 計算垂直位移並誇張化
      float deltaY = newY - dragStartY;
      float extraY = MathUtils.clamp(deltaY * Y_EXAGGERATION, -MAX_EXTRA_Y, MAX_EXTRA_Y);
      lastExtraY = extraY;

      // 把 extraX / extraY 加到畫面上的座標；Y 仍保留 DRAG_VISUAL_Y_OFFSET
      this.pos.set(newX + extraX, newY + DRAG_VISUAL_Y_OFFSET + extraY);
    }

    // Stop isDragging when touch released
    if (isDragging && !InputUtils.isTouched()) {
      isDragging = false;

      // 還原 Y（移除視覺偏移與垂直誇張）
      float logicalY = this.pos.y - DRAG_VISUAL_Y_OFFSET - lastExtraY;

      // 還原 X（移除誇張偏移），使用 lastExtraX
      float logicalX = this.pos.x - lastExtraX;

      // 將 pos 還原為 logical，這樣內部邏輯保持精度
      this.pos.set(logicalX, logicalY);

      // 注意：此時 lastExtraX / lastExtraY 尚存（沒清），讓 onDragStop() 可以讀取 visual position
      onDragStop();

      // Recompute the input range so subsequent touches are detected in the
      // new location. Not doing this is a common cause for "can drag once but
      // not again" because the hit area wasn't updated after the object moved.
      super.initRange();

      // Clear temporary visual offsets (keeps internal logical pos clean).
      clearOffset();

      // reset grabOffset to avoid stale offsets affecting next drag
      grabOffset.set(0f, 0f);
    }
  }

  @Override
  public void onDragStop() {
    // override by subclass
  }

  @Override
  public void onTap() {
  }

  /** 回傳「畫面上看到的座標」(visual position)，包含 Y 視覺偏移與 X/Y 誇張偏移 */
  public Vector2 getVisualPosition() {
    // pos 在停止後是 logical，所以加上 lastExtraX/lastExtraY 與 DRAG_VISUAL_Y_OFFSET
    // 得到 visual
    return new Vector2(this.pos.x + this.lastExtraX,
        this.pos.y + DRAG_VISUAL_Y_OFFSET + this.lastExtraY);
  }

  /** 回傳目前 logical position（等同於 pos 被還原後） */
  public Vector2 getLogicalPosition() {
    return new Vector2(this.pos.x, this.pos.y);
  }

  public void clearOffset() {
    this.lastExtraX = 0f;
    this.lastExtraY = 0f;
  }

  // 可增加 setter 來在執行時調整誇張係數
  public static void setXExaggeration(float x) {
    // 需注意：若要改常數值，可能改為 instance 欄位會更彈性；這裡僅示範
  }
}
