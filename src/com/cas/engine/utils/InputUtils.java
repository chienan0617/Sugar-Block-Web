package com.cas.engine.utils;

import org.jetbrains.annotations.Nullable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

/**
 * InputUtil is a utility class that provides methods for handling input events
 * in the game.
 * It extends the Util class and provides methods to check touch events, mouse
 * position,
 * and whether the touch is within a specified range.
 */
public final class InputUtils extends Utils {
  @Nullable
  public static int[] getPressedDown() {
    if (!isTouched()) {
      return null; // Return -1, -1 if no touch is detected
    }
    return getMousePosition();
  }

  public static int[] getMousePosition() {
    return new int[] { Gdx.input.getX(), Gdx.input.getY() };
  }

  public static boolean isTouched() {
    return Gdx.input.isTouched();
  }

  public static boolean isJustTouched() {
    return Gdx.input.justTouched();
  }

  public static boolean isTouchedUp() {
    return Gdx.input.isButtonJustPressed(Input.Buttons.LEFT);
  }

  public static int[] getDragged() {
    if (!isTouched()) {
      return null; // Return -1, -1 if no touch is detected
    }
    return new int[] { Gdx.input.getDeltaX(), Gdx.input.getDeltaY() };
  }

  public static boolean mouseInRange(int[] range) {
    return (Gdx.input.getX() >= range[0] &&
        Gdx.input.getX() <= range[2] &&
        Gdx.input.getY() >= range[1] &&
        Gdx.input.getY() <= range[3]);
  }

  public static boolean inRange(Vector2 pos, int[] range) {
    return (pos.x >= range[0] &&
        pos.x <= range[2] &&
        pos.y >= range[1] &&
        pos.y <= range[3]);
  }

  public static boolean isTouchInRange(int[] range) {
    return isTouched() && mouseInRange(range);
  }

  public static boolean isJustTouchedInRange(int[] range) {
    return isJustTouched() && mouseInRange(range);
  }

  /**
   * [0] - [1]
   * | |
   * [2] - [3]
   */
  public static boolean isTouchedUpInRange(int[] range) {
    return isTouchedUp() && mouseInRange(range);
  }

  public static boolean isKeyDown(int key) {
    return Gdx.input.isKeyPressed(key);
  }

  // public static boolean isTouchingInRange(int[] range) {
  // return isTouched()
  // }
}
