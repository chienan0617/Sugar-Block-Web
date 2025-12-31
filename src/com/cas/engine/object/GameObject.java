package com.cas.engine.object;

import com.cas.engine.annotation.DebugOnly;
import com.cas.engine.core.ObjectManager;
import com.cas.engine.debug.Debug;
// import com.cas.engine.debug.Debug;
import com.cas.engine.utils.Mth;

public class GameObject extends GamePiece {
  public String name;

  public GameObject(String name) {
    super();

    if (name == null || name.isEmpty() || name.equals("-")) {
      name = randomName();
    }
    this.name = name;
  }

  public void add(GamePiece piece) {
    ObjectManager.add(piece);
  }

  public void remove() {
    ObjectManager.remove(this);
  }

  public void remove(GamePiece piece) {
    ObjectManager.remove(piece);
  }

  public static String randomName() {
    return Integer.toString(Mth.nextInt());
  }

  @DebugOnly
  public static void debug(Object... obj) {
    Debug.log(obj);
  }
}
