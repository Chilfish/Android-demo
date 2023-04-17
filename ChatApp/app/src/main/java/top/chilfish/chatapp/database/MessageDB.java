package top.chilfish.chatapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import top.chilfish.chatapp.entity.Message;
import top.chilfish.chatapp.entity.Profile;

public class MessageDB extends BaseDatabase {

  private static final String TABLE_NAME = "messages";
  private static final String COLUMN_ID = "id";
  private static final String COLUMN_CONTENT = "content";
  private static final String COLUMN_RECEIVER_ID = "receiver_id";
  private static final String COLUMN_SENDER_ID = "sender_id";
  private static final String COLUMN_TIME = "time";
  private static final String COLUMN_RIGHT = "isRight";

  static final String CREATE_MESSAGE =
      "CREATE TABLE " + TABLE_NAME + " (" +
          COLUMN_ID + " TEXT ," +
          COLUMN_CONTENT + " TEXT," +
          COLUMN_RECEIVER_ID + " TEXT," +
          COLUMN_SENDER_ID + " TEXT," +
          COLUMN_TIME + " TEXT," +
          COLUMN_RIGHT + " TEXT)";

  private static final String SQL_DELETE_ENTRIES =
      "DROP TABLE IF EXISTS " + TABLE_NAME;

  private static final String[] getProjection = {
      COLUMN_ID,
      COLUMN_CONTENT,
      COLUMN_RECEIVER_ID,
      COLUMN_SENDER_ID,
      COLUMN_TIME,
      COLUMN_RIGHT
  };

  public MessageDB(Context context) {
    super(context);
  }

  private ContentValues putValues(Message message) {
    ContentValues values = new ContentValues();
    values.put(COLUMN_ID, message.getId());
    values.put(COLUMN_CONTENT, message.getContent());
    values.put(COLUMN_RECEIVER_ID, message.getReceiverId());
    values.put(COLUMN_SENDER_ID, message.getSenderId());
    values.put(COLUMN_TIME, message.getTimeString());
    values.put(COLUMN_RIGHT, message.isRightString());
    return values;
  }

  private List<Message> getMessages(Cursor cursor) {
    List<Message> messages = new ArrayList<>();
    while (cursor.moveToNext()) {
      Message message = new Message();
      message.setContent(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)));
      message.setReceiverId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECEIVER_ID)));
      message.setSenderId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SENDER_ID)));
      message.setTime(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME)));
      message.setId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)));
      message.setRight(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RIGHT)));

      messages.add(message);
    }
    cursor.close();
    return messages;
  }

  public void insert(Message message) {
    SQLiteDatabase db = getWritableDatabase();
    db.insert(
        TABLE_NAME,
        null,
        putValues(message)
    );
  }

  public void update(Message message) {
    SQLiteDatabase db = getWritableDatabase();

    String selection = COLUMN_ID + " = ?";
    String[] args = {String.valueOf(message.getId())};
    db.update(TABLE_NAME,
        putValues(message),
        selection,
        args
    );
  }

  public List<Message> getAll() {
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
    return getMessages(cursor);
  }

  // Get messages by idA and idB
  public List<Message> getById(String id) {
    String curId = Profile.load().getUid();

    SQLiteDatabase db = getReadableDatabase();
    String selection = (COLUMN_RECEIVER_ID + " = ? AND " + COLUMN_SENDER_ID + " = ?") +
        " OR " + (COLUMN_SENDER_ID + " = ? AND " + COLUMN_RECEIVER_ID + " = ?");
    String[] args = {id, curId, id, curId};

    Cursor cursor = db.query(
        TABLE_NAME,
        getProjection,
        selection,
        args,
        null,
        null,
        null
    );
    return getMessages(cursor);
  }

  public boolean delete(Message message) {
    SQLiteDatabase db = getWritableDatabase();
    String selection = COLUMN_ID + " = ?";
    String[] args = {String.valueOf(message.getId())};

    int result = db.delete(
        TABLE_NAME,
        selection,
        args
    );
    return result > 0;
  }
}