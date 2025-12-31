package com.cas.engine.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.cas.engine.GameConfig;
import com.cas.engine.object.GameObject;
import com.cas.engine.object.GamePiece;

public class ObjectManager {
  public static final CopyOnWriteArrayList<GamePiece> pieces = new CopyOnWriteArrayList<>();

  public static final List<GamePiece> cachedWorldPieces = new ArrayList<>();
  public static final List<GamePiece> cachedUIPieces = new ArrayList<>();

  private static volatile boolean dirty = true;

  private static final Comparator<GamePiece> RENDER_SORTER = (o1, o2) -> Integer.compare(o1.order, o2.order);

  public static void updateRenderCache() {
    if (dirty) {
      cachedWorldPieces.clear();
      cachedUIPieces.clear();

      // 分類
      for (GamePiece p : pieces) {
        if (!p.visible)
          continue;

        if (p.isScreenSpace()) {
          cachedUIPieces.add(p);
        } else {
          cachedWorldPieces.add(p);
        }
      }

      Collections.sort(cachedWorldPieces, RENDER_SORTER);
      Collections.sort(cachedUIPieces, RENDER_SORTER);

      dirty = false;
    }
  }

  public static void notifyChanged() {
    dirty = true;
  }

  public static void ensureStart(GamePiece piece) {
    if (!piece.initYet) {
      piece.awake();
      piece.init();
      piece.initYet = true;
    }
  }

  public static void add(GamePiece piece) {
    ensureStart(piece);

    if (GameConfig.DEBUG_MODE) {
      piece.debugInit();
    }

    pieces.add(piece);
    notifyChanged(); // 標記髒數據
  }

  public static GamePiece get(long id) {
    for (GamePiece p : pieces) {
      if (p.ID == id)
        return p;
    }
    return null;
  }

  public static GamePiece get(String name) {
    for (GamePiece p : pieces) {
      if (p instanceof GameObject && ((GameObject) p).name.equals(name)) {
        return p;
      }
    }
    return null;
  }

  public static void ensureEnd(GamePiece piece) {
    piece.end();
    piece.dispose();
  }

  public static void remove(GamePiece piece) {
    ensureEnd(piece);
    pieces.remove(piece);
    notifyChanged(); // 標記髒數據
  }

  public static void remove(int index) {
    if (index >= 0 && index < pieces.size()) {
      var old = pieces.remove(index);
      ensureEnd(old);
      notifyChanged(); // 標記髒數據
    }
  }
}
