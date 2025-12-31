package com.cas.engine.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.AsyncTask;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

public class FileHandler {

  private static final AsyncExecutor executor = new AsyncExecutor(1);
  private static final ConcurrentHashMap<String, Object> fileLocks = new ConcurrentHashMap<>();

  private FileHandler() {
  }

  private static Object getLock(String key) {
    Object lock = fileLocks.get(key);
    if (lock == null) {
      Object newLock = new Object();
      lock = fileLocks.putIfAbsent(key, newLock);
      if (lock == null)
        lock = newLock;
    }
    return lock;
  }

  public static void ensureDirectory(FileHandle dir) {
    if (!dir.exists())
      dir.mkdirs();
  }

  public static boolean writeStringAtomically(FileHandle target, String text, boolean append) {
    ensureDirectory(target.parent());
    Object lock = getLock(target.path());
    synchronized (lock) {
      FileHandle tmp = target.sibling(target.name() + ".tmp");
      try {
        tmp.writeString(text, append);
        if (target.exists())
          target.delete();
        File tmpFile = tmp.file();
        boolean ok = tmpFile.renameTo(target.file());
        if (!ok) {
          byte[] b = tmp.readBytes();
          target.writeBytes(b, false);
          tmp.delete();
        }
        return true;
      } catch (Exception e) {
        Gdx.app.error("FileHandler", "writeStringAtomically failed: " + target.path(), e);
        try {
          if (tmp.exists())
            tmp.delete();
        } catch (Exception ignored) {
        }
        return false;
      }
    }
  }

  public static void writeStringAsync(final FileHandle target, final String text, final boolean append) {
    executor.submit(new AsyncTask<Void>() {
      @Override
      public Void call() {
        writeStringAtomically(target, text, append);
        return null;
      }
    });
  }

  public static boolean writeBytesAtomically(FileHandle target, byte[] data) {
    ensureDirectory(target.parent());
    Object lock = getLock(target.path());
    synchronized (lock) {
      FileHandle tmp = target.sibling(target.name() + ".tmp");
      try {
        tmp.writeBytes(data, false);
        if (target.exists())
          target.delete();
        File tmpFile = tmp.file();
        boolean ok = tmpFile.renameTo(target.file());
        if (!ok) {
          target.writeBytes(tmp.readBytes(), false);
          tmp.delete();
        }
        return true;
      } catch (Exception e) {
        Gdx.app.error("FileHandler", "writeBytesAtomically failed: " + target.path(), e);
        try {
          if (tmp.exists())
            tmp.delete();
        } catch (Exception ignored) {
        }
        return false;
      }
    }
  }

  public static void writeBytesAsync(final FileHandle target, final byte[] data) {
    executor.submit(new AsyncTask<Void>() {
      @Override
      public Void call() {
        writeBytesAtomically(target, data);
        return null;
      }
    });
  }

  public static String readString(FileHandle target) {
    if (!target.exists())
      return null;
    try {
      return target.readString();
    } catch (Exception e) {
      Gdx.app.error("FileHandler", "readString failed: " + target.path(), e);
      return null;
    }
  }

  public static byte[] readBytes(FileHandle target) {
    if (!target.exists())
      return null;
    try {
      return target.readBytes();
    } catch (Exception e) {
      Gdx.app.error("FileHandler", "readBytes failed: " + target.path(), e);
      return null;
    }
  }

  public static boolean delete(FileHandle target) {
    try {
      if (target.exists())
        return target.delete();
      return true;
    } catch (Exception e) {
      Gdx.app.error("FileHandler", "delete failed: " + target.path(), e);
      return false;
    }
  }

  public static boolean exists(FileHandle target) {
    return target.exists();
  }

  public static FileHandle get(String pat) {
    try {
      return Gdx.files.internal(pat);
    } catch (Exception e) {
      Gdx.app.error("FileHandler", "get failed: " + pat, e);
      return null;
    }
  }

  public static void shutdown() {
    try {
      executor.dispose();
    } catch (Exception ignored) {
    }
  }
}
