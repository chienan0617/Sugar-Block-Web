package dev.cas.game.block2;

import com.badlogic.gdx.ApplicationAdapter;
import com.cas.engine.core.EngineLoop;

public class Main extends ApplicationAdapter {
  @Override
  public void create() {
    EngineLoop.onCreate();
  }

  @Override
  public void render() {
    EngineLoop.onRender();
  }

  @Override
  public void resize(int width, int height) {
    EngineLoop.onResize(width, height);
  }

  @Override
  public void pause() {
    EngineLoop.onPause();
  }

  @Override
  public void resume() {
    EngineLoop.onResume();
  }

  @Override
  public void dispose() {
    EngineLoop.onDispose();
  }
}
