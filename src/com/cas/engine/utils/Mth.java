package com.cas.engine.utils;

import java.util.Random;

import com.cas.engine.GameInstance;

public final class Mth {
  public static final Random random = GameInstance.RANDOM;

  public static int nextInt(int i) {
    return random.nextInt(i);
  }

  public static int nextInt() {
    return random.nextInt();
  }

  public static float nextFloat() {
    return random.nextFloat();
  }

  public static float nextFloat(float i) {
    return random.nextFloat(i);
  }

  public static long nextLong(long i) {
    return random.nextLong(i);
  }

  public static long nextLong() {
    return random.nextLong();
  }

  public static int round(float n) {
    return Math.round(n);
  }

  public static int r(float n) {
    return Math.round(n);
  }

  public static int ceil(float n) {
    return (int) Math.ceil(n);
  }

  public static double log(double value, double base) {
    return Math.log(value) / Math.log(base);
  }
}
