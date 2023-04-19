package top.chilfish.chatapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import top.chilfish.chatapp.entity.Profile

class ContactDB(context: Context?) : BaseDatabase(context) {
    private fun putValues(profile: Profile): ContentValues {
        val values = ContentValues()
        values.put(COLUMN_UID, profile.uid)
        values.put(COLUMN_NAME, profile.name)
        values.put(COLUMN_AVATAR, profile.avatar)
        values.put(COLUMN_EMAIL, profile.email)
        values.put(COLUMN_BIO, profile.bio)
        return values
    }

    private fun getProfiles(cursor: Cursor): List<Profile> {
        val profiles: MutableList<Profile> = ArrayList()
        while (cursor.moveToNext()) {
            val profile = Profile()
            profile.uid = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UID))
            profile.name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            profile.avatar = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AVATAR))
            profile.email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
            profile.bio = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIO))
            profiles.add(profile)
        }
        cursor.close()
        return profiles
    }

    fun insert(profile: Profile): Boolean {
        val db = writableDatabase
        val result = db.insert(
            TABLE_NAME,
            null,
            putValues(profile)
        )
        return result != -1L
    }

    fun update(profile: Profile): Boolean {
        val db = writableDatabase
        val selection = "$COLUMN_UID=?"
        val args = arrayOf(profile.uid)
        val result = db.update(
            TABLE_NAME,
            putValues(profile),
            selection,
            args
        )
        return result > 0
    }

    fun delete(uid: String): Boolean {
        val db = writableDatabase
        val selection = "$COLUMN_UID=?"
        val args = arrayOf(uid)
        val result = db.delete(
            TABLE_NAME,
            selection,
            args
        )
        return result > 0
    }

    fun getById(uid: String): Profile {
        val db = readableDatabase
        val selection = "$COLUMN_UID=?"
        val args = arrayOf(uid)
        val cursor = db.query(
            TABLE_NAME,
            getProjection,
            selection,
            args,
            null,
            null,
            null
        )
        return getProfiles(cursor)[0]
    }

    val all: List<Profile>
        get() {
            val db = readableDatabase
            val cursor = db.query(
                TABLE_NAME,
                getProjection,
                null,
                null,
                null,
                null,
                null
            )
            return getProfiles(cursor)
        }

    companion object {
        private const val TABLE_NAME = "contacts"
        private const val COLUMN_UID = "uid"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_AVATAR = "avatar"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_BIO = "bio"
        const val CREATE_CONTACT = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_UID + " TEXT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_AVATAR + " TEXT," +
                COLUMN_EMAIL + " TEXT," +
                COLUMN_BIO + " TEXT)"
        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
        private val getProjection = arrayOf(
            COLUMN_UID,
            COLUMN_NAME,
            COLUMN_AVATAR,
            COLUMN_EMAIL,
            COLUMN_BIO
        )
    }
}