package com.cas.engine;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class GameInstance {
  public static Random RANDOM = new Random();
  public static SpriteBatch objectBatch = new SpriteBatch();
  public static SpriteBatch uiBatch = new SpriteBatch();
  public static final ModelBuilder modelBuilder = new ModelBuilder();
  public static final Environment environment = new Environment();
  public static final ModelBatch modelBatch = new ModelBatch();
}
