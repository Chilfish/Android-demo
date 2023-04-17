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

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.beginTransaction();
    try {
      db.execSQL(ChatListDB.CREATE_CHATLIST);
      db.execSQL(MessageDB.CREATE_MESSAGE);
      db.execSQL(ContactDB.CREATE_CONTACT);
      db.setTransactionSuccessful();
    } finally {
      db.endTransaction();
    }
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int i, int i1) {
  }
}

