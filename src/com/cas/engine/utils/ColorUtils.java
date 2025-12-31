package com.cas.engine.utils;

import com.badlogic.gdx.graphics.Color;

public final class ColorUtils extends Utils {
  public static Color hex(String hex) {
    // Remove # if present
    String cleanHex = hex.startsWith("#") ? hex.substring(1) : hex;

    // Expect formats: RGB, RRGGBB, or RRGGBBAA
    switch (cleanHex.length()) {
      case 3: { // short RGB
        int r = Integer.parseInt(String.valueOf(cleanHex.charAt(0)) + cleanHex.charAt(0), 16);
        int g = Integer.parseInt(String.valueOf(cleanHex.charAt(1)) + cleanHex.charAt(1), 16);
        int b = Integer.parseInt(String.valueOf(cleanHex.charAt(2)) + cleanHex.charAt(2), 16);
        return new Color(r / 255f, g / 255f, b / 255f, 1f);
      }
      case 6: { // RRGGBB
        int r = Integer.parseInt(cleanHex.substring(0, 2), 16);
        int g = Integer.parseInt(cleanHex.substring(2, 4), 16);
        int b = Integer.parseInt(cleanHex.substring(4, 6), 16);
        return new Color(r / 255f, g / 255f, b / 255f, 1f);
      }
      case 8: { // RRGGBBAA
        int r = Integer.parseInt(cleanHex.substring(0, 2), 16);
        int g = Integer.parseInt(cleanHex.substring(2, 4), 16);
        int b = Integer.parseInt(cleanHex.substring(4, 6), 16);
        int a = Integer.parseInt(cleanHex.substring(6, 8), 16);
        return new Color(r / 255f, g / 255f, b / 255f, a / 255f);
      }
      default:
        throw new IllegalArgumentException("Invalid hex color format: " + hex);
    }
  }

  public static Color darkerColor(Color c, float r) {
    r = r > 1 ? r - 1 : r;
    return new Color(c.r * r, c.g * r, c.b * r, c.a);
  }

  public static Color lighterColor(Color c, float r) {
    r = r < 1 ? r - 1 : r;
    return new Color(c.r * r, c.g * r, c.b * r, c.a);
  }
}
