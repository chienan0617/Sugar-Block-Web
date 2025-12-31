package com.cas.engine.exception;

public class ThrowError {
  public static void illegalArgument(String cause) {
    throw new IllegalArgumentException(cause);
  }

  public static void nullPointer(Object nullObj) {
    throw new NullPointerException(nullObj.toString() + " is null");
  }

  public static void indexOutOfBounds(int index) {
    throw new IndexOutOfBoundsException(index);
  }

  public static void checkOrThrow(Object uncheck) {
    if (uncheck == null) {
      nullPointer(uncheck);
    }
  }
}
