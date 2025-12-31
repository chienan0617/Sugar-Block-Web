package com.cas.engine.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.graphics.GL20;
import com.cas.engine.GameConfig;
import com.cas.engine.GameInstance;
import com.cas.engine.core.threads.DebugThread;
import com.cas.engine.core.threads.UpdateThread;
import com.cas.engine.model.Camera2D;
import com.cas.engine.object.GamePiece;
import com.cas.engine.object.RootObject;

public class EngineLoop {
  public static Thread updateThread;
  public static Thread debugThread;
  public static final boolean ENABLE_DEBUG_THREAD = false;
  public static final boolean ENABLE_UPDATE_THREAD = false;
  private static final Matrix4 SCREEN_PROJECTION = new Matrix4();

  @SuppressWarnings("unused")
  public static void onCreate() {
    addNecessaryObject();

    if (ENABLE_UPDATE_THREAD) {
      updateThread = new Thread(new UpdateThread(), "Engine-Update-Thread");
      updateThread.start();
    }

    if (ENABLE_DEBUG_THREAD && GameConfig.DEBUG_MODE) {
      debugThread = new Thread(new DebugThread(), "Engine-Debug-Thread");
      debugThread.start();
    }
  }

  private static void addNecessaryObject() {
    ObjectManager.add(new Camera2D("[camera]"));
    ObjectManager.add(new RootObject("[root]"));
  }

  public static void onRender() {
    Camera2D.camera.update();

    ObjectManager.updateRenderCache();

    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    GameInstance.objectBatch.setProjectionMatrix(Camera2D.camera.combined);
    GameInstance.objectBatch.begin();

    for (int i = 0; i < ObjectManager.cachedWorldPieces.size(); i++) {
      ObjectManager.cachedWorldPieces.get(i).render();
    }

    GameInstance.objectBatch.end();

    SCREEN_PROJECTION.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    GameInstance.uiBatch.setProjectionMatrix(SCREEN_PROJECTION);
    GameInstance.uiBatch.begin();

    for (int i = 0; i < ObjectManager.cachedUIPieces.size(); i++) {
      ObjectManager.cachedUIPieces.get(i).render();
    }

    if (!ENABLE_UPDATE_THREAD) {
      float delta = Gdx.graphics.getDeltaTime();

      for (int i = 0; i < ObjectManager.cachedUIPieces.size(); i++) {
        ObjectManager.cachedUIPieces.get(i).update(delta);
      }

      for (int i = 0; i < ObjectManager.cachedWorldPieces.size(); i++) {
        ObjectManager.cachedWorldPieces.get(i).update(delta);
      }
    }

    GameInstance.uiBatch.end();
  }

  public static void onDispose() {
    // 停止線程
    // stopThread(updateThread);
    // stopThread(debugThread);
    try {
      updateThread.interrupt();
      debugThread.interrupt();
    } catch (NullPointerException e) {

    } catch (Exception e) {

    }

    ObjectManager.pieces.forEach(e -> {
      e.end();
      e.dispose();
    });
  }

  // private static void stopThread(Thread thread) {
  // if (thread != null && thread.isAlive()) {
  // if (thread.getState() != Thread.State.NEW) {
  // try {
  // // 嘗試中斷線程
  // thread.interrupt();
  // thread.join(1000);
  // } catch (InterruptedException e) {
  // Thread.currentThread().interrupt();
  // }
  // }
  // }
  // }

  public static void onPause() {
    ObjectManager.pieces.forEach(GamePiece::pause);
  }

  public static void onResume() {
    ObjectManager.pieces.forEach(GamePiece::resume);
  }

  public static void onResize(int w, int h) {
    ObjectManager.pieces.forEach(e -> e.resize(w, h));
  }
}
