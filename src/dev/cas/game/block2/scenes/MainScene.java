package dev.cas.game.block2.scenes;

import com.cas.engine.component.Scene;
import com.cas.engine.object.GamePiece;
import com.cas.engine.scene.SceneManager;

import dev.cas.game.block2.dropping.DroppingScene;
import dev.cas.game.block2.slide.SlideScene;
import dev.cas.game.block2.standard.StandardScene;

public class MainScene extends Scene {
  public MainScene(String name, String sceneName) {
    super(name, sceneName, new GamePiece[] {

    });

    SceneManager.add(new StandardScene(null, "standard"));
    SceneManager.add(new DroppingScene(null, "dropping"));
    SceneManager.add(new SlideScene(null, "slide"));
  }
}
