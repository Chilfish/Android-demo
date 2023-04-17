package top.chilfish.chatapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import top.chilfish.chatapp.entity.ChatItem;
import top.chilfish.chatapp.entity.Profile;

public class ChatListDB extends BaseDatabase {
  private static final String TABLE_NAME = "chat_list";
  private static final String COLUMN_ID = "id";
  private static final String COLUMN_TIME = "time";
  private static final String COLUMN_CONTENT = "content";
  private static final String COLUMN_PROFILE = "profile";

  static final String CREATE_CHATLIST =
      "CREATE TABLE " + TABLE_NAME + " (" +
          COLUMN_ID + " TEXT," +
          COLUMN_TIME + " TEXT," +
          COLUMN_CONTENT + " TEXT," +
          COLUMN_PROFILE + " TEXT)";

  private static final String SQL_DELETE_ENTRIES =
      "DROP TABLE IF EXISTS " + TABLE_NAME;

  private static final String[] getProjection = {
      COLUMN_ID,
      COLUMN_TIME,
      COLUMN_CONTENT,
      COLUMN_PROFILE
  };

  public ChatListDB(Context context) {
    super(context);
  }

  private ContentValues putValues(ChatItem chatItem) {
    ContentValues values = new ContentValues();
    values.put(COLUMN_ID, chatItem.getProfile().getUid());
    values.put(COLUMN_TIME, chatItem.getTime());
    values.put(COLUMN_CONTENT, chatItem.getContent());
    values.put(COLUMN_PROFILE, chatItem.getProfileJson());
    return values;
  }

  private List<ChatItem> getChatList(Cursor cursor) {
    List<ChatItem> chatList = new ArrayList<>();
    while (cursor.moveToNext()) {
      String time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME));
      String content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT));
      String profileJson = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROFILE));
      Profile profile = Profile.getProfile(profileJson);

      ChatItem chatItem = new ChatItem(profile, content, time);
      chatList.add(chatItem);
    }
    cursor.close();
    return chatList;
  }

  public void insert(ChatItem chatItem) {
    SQLiteDatabase db = getWritableDatabase();
    db.insert(
        TABLE_NAME,
        null,
        putValues(chatItem)
    );
  }

  public void update(ChatItem chatItem) {
    SQLiteDatabase db = getWritableDatabase();
    db.update(
        TABLE_NAME,
        putValues(chatItem),
        COLUMN_ID + " = ?",
        new String[]{chatItem.getProfile().getUid()}
    );
  }

  public List<ChatItem> getAll() {
    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor = db.query(
        TABLE_NAME,
        getProjection,
        null,
        null,
        null,
        null,
        null
    );
    return getChatList(cursor);
  }

  public ChatItem getById(String id) {
    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor = db.query(
        TABLE_NAME,
        getProjection,
        COLUMN_ID + " = ?",
        new String[]{id},
        null,
        null,
        null
    );
    List<ChatItem> chatList = getChatList(cursor);
    if (chatList.size() == 0) {
      return null;
    }
    return chatList.get(0);
  }

  public void deleteById(String id) {
    SQLiteDatabase db = getWritableDatabase();
    db.delete(
        TABLE_NAME,
        COLUMN_ID + " = ?",
        new String[]{id}
    );
  }

  public void deleteAll() {
    SQLiteDatabase db = getWritableDatabase();
    db.delete(
        TABLE_NAME,
        null,
        null
    );
  }
}
