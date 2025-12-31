package com.cas.engine.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.cas.engine.object.GameObject;

public class Camera2D extends GameObject {
  public static final OrthographicCamera camera = new OrthographicCamera();
  public static final Vector3 originPosition = camera.position;

  public Camera2D(String name) {
    super(name);
    camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    // throw new CasException("Castudio (c) copyright");
  }

  @Override
  public void update(float delta) {
    camera.update();
  }

  public static void resetPosition() {
    camera.position.set(originPosition);
  }

  public static void translate(Vector3 vec) {
    camera.translate(vec);
  }

  public static void translate(float x, float y, float z) {
    camera.translate(x, y, z);
  }
}
