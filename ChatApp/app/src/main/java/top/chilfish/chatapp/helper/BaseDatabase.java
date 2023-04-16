package top.chilfish.chatapp.helper;

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

  @Override
  public void onCreate(SQLiteDatabase db) {
    createTable(db);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    dropTable(db);
    createTable(db);
  }

  public abstract void createTable(SQLiteDatabase db);

  public abstract void dropTable(SQLiteDatabase db);
}
