package top.chilfish.chatapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public abstract class BaseDatabase extends SQLiteOpenHelper {

  public static final String DB_NAME = "chat-app.db";
  public static final int DB_VERSION = 1;

  public BaseDatabase(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory) {
    super(context, DB_NAME, factory, DB_VERSION);
  }

  public BaseDatabase(@Nullable Context context) {
    super(context, DB_NAME, null, DB_VERSION);
  }
}
