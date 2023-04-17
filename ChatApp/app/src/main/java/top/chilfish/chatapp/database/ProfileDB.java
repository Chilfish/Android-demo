package top.chilfish.chatapp.helper.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import top.chilfish.chatapp.entity.Profile;

public class ProfileDB extends BaseDatabase {
  private static final String TABLE_NAME = "profiles";
  private static final String COLUMN_UID = "uid";
  private static final String COLUMN_NAME = "name";
  private static final String COLUMN_AVATAR = "avatar";
  private static final String COLUMN_EMAIL = "email";
  private static final String COLUMN_BIO = "bio";

  private static final String CREATE_TABLE =
      "CREATE TABLE " + TABLE_NAME + " (" +
          COLUMN_UID + " TEXT PRIMARY KEY," +
          COLUMN_NAME + " TEXT," +
          COLUMN_AVATAR + " TEXT," +
          COLUMN_EMAIL + " TEXT," +
          COLUMN_BIO + " TEXT)";

  public ProfileDB(@Nullable Context context) {
    super(context);
  }

  @Override
  public void createTable(SQLiteDatabase db) {
    db.execSQL(CREATE_TABLE);
  }

  @Override
  public void dropTable(SQLiteDatabase db) {
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
  }

  public boolean insertProfile(Profile profile) {
    SQLiteDatabase db = getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(COLUMN_UID, profile.getUid());
    values.put(COLUMN_NAME, profile.getName());
    values.put(COLUMN_AVATAR, profile.getAvatar());
    values.put(COLUMN_EMAIL, profile.getEmail());
    values.put(COLUMN_BIO, profile.getBio());
    long result = db.insert(TABLE_NAME, null, values);
    return result != -1;
  }

  public boolean updateProfile(Profile profile) {
    SQLiteDatabase db = getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(COLUMN_NAME, profile.getName());
    values.put(COLUMN_AVATAR, profile.getAvatar());
    values.put(COLUMN_EMAIL, profile.getEmail());
    values.put(COLUMN_BIO, profile.getBio());
    int result = db.update(TABLE_NAME, values, COLUMN_UID + "=?", new String[]{profile.getUid()});
    return result > 0;
  }

  public boolean deleteProfile(String uid) {
    SQLiteDatabase db = getWritableDatabase();
    int result = db.delete(TABLE_NAME, COLUMN_UID + "=?", new String[]{uid});
    return result > 0;
  }

  @SuppressLint("Range")
  public Profile getProfile(String uid) {
    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor = db.query(TABLE_NAME, null, COLUMN_UID + "=?", new String[]{uid}, null, null, null);
    if (!(cursor != null && cursor.moveToFirst())) {
      return null;
    }

    Profile profile = new Profile();
    profile.setUid(cursor.getString(cursor.getColumnIndex(COLUMN_UID)));
    profile.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
    profile.setAvatar(cursor.getString(cursor.getColumnIndex(COLUMN_AVATAR)));
    profile.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
    profile.setBio(cursor.getString(cursor.getColumnIndex(COLUMN_BIO)));
    cursor.close();
    return profile;

  }

  @SuppressLint("Range")
  public List<Profile> getAllProfiles() {
    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
    List<Profile> profiles = new ArrayList<>();

    if (!(cursor != null && cursor.moveToFirst())) {
      return profiles;
    }

    do {
      Profile profile = new Profile();
      profile.setUid(cursor.getString(cursor.getColumnIndex(COLUMN_UID)));
      profile.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
      profile.setAvatar(cursor.getString(cursor.getColumnIndex(COLUMN_AVATAR)));
      profile.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
      profile.setBio(cursor.getString(cursor.getColumnIndex(COLUMN_BIO)));
      profiles.add(profile);
    } while (cursor.moveToNext());
    cursor.close();
    return profiles;
  }
}
