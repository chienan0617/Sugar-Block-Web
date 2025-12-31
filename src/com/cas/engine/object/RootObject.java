package com.cas.engine.object;

import com.cas.engine.component.Scene;
import com.cas.engine.scene.SceneManager;

import dev.cas.game.block2.scenes.MainScene;

public class RootObject extends GameObject {
  public RootObject(String name) {
    super(name);
  }

  @Override
  public void init() {
    // SceneManager.add(new Scene(null, "s1", new GamePiece[] {
    // new Canvas("c", new Size(100, 100), new Vector2(50,
    // 50)).fill(Colors.LILAC_LIGHT).done(),
    // new EventListener("e", new Size(100, 100), new Vector2(50, 50))
    // .setOnPressed(() -> {
    // SceneManager.switchTo("[root]");
    // }),
    // }));

    SceneManager.add(new Scene(null, "[root]", new GamePiece[] {
        // new Canvas("c", new Size(50, 50), new Vector2(50,
        // 50)).fill(Colors.ALMOND_MILK).done(),
        // new EventListener("e", new Size(50, 50), new Vector2(50, 50)).setOnPressed(()
        // -> SceneManager.switchTo("s2")),
    }));

    SceneManager.add(new MainScene(null, "main"));
    SceneManager.switchTo("standard");

    // SceneManager.add(new Scene(null, "main", ));

    // SceneManager.add(new Scene(null, "s2", new GamePiece[] {
    // new Canvas("c", new Size(150, 150), new Vector2(50,
    // 50)).fill(Colors.FOREST_GREEN).done(),
    // new EventListener("e", new Size(150, 150), new Vector2(50,
    // 50)).setOnPressed(() -> SceneManager.switchTo("s1")),
    // }));
  }
}
