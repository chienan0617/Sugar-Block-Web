package com.cas.engine.scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cas.engine.annotation.Nullable;
import com.cas.engine.component.Scene;
import com.cas.engine.core.ObjectManager;

public class SceneManager {
  public static final List<Scene> scenes = Collections.synchronizedList(new ArrayList<>());
  private static final String rootSceneName = "[root]";
  public static String currentSceneName = rootSceneName;

  public static void add(Scene scene) {
    scenes.add(scene);

    if (rootSceneName.equals(scene.sceneName)) {
      scene.loadPieces();
      currentSceneName = rootSceneName;
    }
  }

  @Nullable
  public static Scene get(String name) {
    synchronized (scenes) {
      return scenes
          .stream()
          .filter(e -> e != null && name.equals(e.sceneName))
          .findFirst()
          .orElse(null);
    }
  }

  public static void remove(String name) {
    Scene scene = get(name);

    if (scene != null) {
      try {
        scene.unloadPieces();
      } catch (Exception ex) {
        ex.printStackTrace();
      }

      scenes.remove(scene);

      try {
        ObjectManager.ensureEnd(scene);
      } catch (Throwable t) {
      }

      if (name.equals(currentSceneName)) {
        currentSceneName = "root";
      }
    }
  }

  public static void switchTo(String sceneName) {
    Scene next = get(sceneName);
    Scene current = get(currentSceneName);

    if (next == null) {
      throw new NullPointerException("sceneName: " + sceneName + " Not found !");
    }

    if (current != null && current != next) {
      current.unloadPieces();
    }

    next.loadPieces();
    currentSceneName = sceneName; // **一定要更新目前場景名稱**
  }
}
