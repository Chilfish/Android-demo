package top.chilfish.chatapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import top.chilfish.chatapp.entity.Profile;

public class ContactDB extends BaseDatabase {
  private static final String TABLE_NAME = "contacts";
  private static final String COLUMN_UID = "uid";
  private static final String COLUMN_NAME = "name";
  private static final String COLUMN_AVATAR = "avatar";
  private static final String COLUMN_EMAIL = "email";
  private static final String COLUMN_BIO = "bio";

  static final String CREATE_CONTACT =
      "CREATE TABLE " + TABLE_NAME + " (" +
          COLUMN_UID + " TEXT," +
          COLUMN_NAME + " TEXT," +
          COLUMN_AVATAR + " TEXT," +
          COLUMN_EMAIL + " TEXT," +
          COLUMN_BIO + " TEXT)";

  private static final String SQL_DELETE_ENTRIES =
      "DROP TABLE IF EXISTS " + TABLE_NAME;

  private static final String[] getProjection = {
      COLUMN_UID,
      COLUMN_NAME,
      COLUMN_AVATAR,
      COLUMN_EMAIL,
      COLUMN_BIO
  };


  public ContactDB(@Nullable Context context) {
    super(context);
  }

  private ContentValues putValues(Profile profile) {
    ContentValues values = new ContentValues();
    values.put(COLUMN_UID, profile.getUid());
    values.put(COLUMN_NAME, profile.getName());
    values.put(COLUMN_AVATAR, profile.getAvatar());
    values.put(COLUMN_EMAIL, profile.getEmail());
    values.put(COLUMN_BIO, profile.getBio());
    return values;
  }

  private List<Profile> getProfiles(Cursor cursor) {
    List<Profile> profiles = new ArrayList<>();
    while (cursor.moveToNext()) {
      Profile profile = new Profile();
      profile.setUid(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UID)));
      profile.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
      profile.setAvatar(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AVATAR)));
      profile.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)));
      profile.setBio(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIO)));

      profiles.add(profile);
    }
    cursor.close();
    return profiles;
  }

  public boolean insert(Profile profile) {
    SQLiteDatabase db = getWritableDatabase();

    long result = db.insert(
        TABLE_NAME,
        null,
        putValues(profile)
    );
    return result != -1;
  }

  public boolean update(Profile profile) {
    SQLiteDatabase db = getWritableDatabase();
    String selection = COLUMN_UID + "=?";
    String[] args = {profile.getUid()};

    int result = db.update(
        TABLE_NAME,
        putValues(profile),
        selection,
        args
    );
    return result > 0;
  }

  public boolean delete(String uid) {
    SQLiteDatabase db = getWritableDatabase();
    String selection = COLUMN_UID + "=?";
    String[] args = {uid};

    int result = db.delete(
        TABLE_NAME,
        selection,
        args
    );
    return result > 0;
  }

  public Profile getById(String uid) {
    SQLiteDatabase db = getReadableDatabase();
    String selection = COLUMN_UID + "=?";
    String[] args = {uid};

    Cursor cursor = db.query(
        TABLE_NAME,
        getProjection,
        selection,
        args,
        null,
        null,
        null);

    return getProfiles(cursor).get(0);
  }

  public List<Profile> getAll() {
    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor = db.query(
        TABLE_NAME,
        getProjection,
        null,
        null,
        null,
        null,
        null);
    return getProfiles(cursor);
  }
}
