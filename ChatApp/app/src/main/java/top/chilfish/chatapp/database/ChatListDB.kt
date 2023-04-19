package top.chilfish.chatapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import top.chilfish.chatapp.entity.ChatItem
import top.chilfish.chatapp.entity.Profile.Companion.getProfile

class ChatListDB(context: Context?) : BaseDatabase(context) {
    private fun putValues(chatItem: ChatItem): ContentValues {
        val values = ContentValues()
        values.put(COLUMN_ID, chatItem.profile.uid)
        values.put(COLUMN_TIME, chatItem.time)
        values.put(COLUMN_CONTENT, chatItem.content)
        values.put(COLUMN_PROFILE, chatItem.profileJson)
        return values
    }

    private fun getChatList(cursor: Cursor): List<ChatItem> {
        val chatList: MutableList<ChatItem> = ArrayList()
        while (cursor.moveToNext()) {
            val time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
            val profileJson = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROFILE))
            val profile = getProfile(profileJson)
            val chatItem = ChatItem(profile, content, time)
            chatList.add(chatItem)
        }
        cursor.close()
        return chatList
    }

    fun insert(chatItem: ChatItem) {
        val db = writableDatabase
        db.insert(
            TABLE_NAME,
            null,
            putValues(chatItem)
        )
    }

    fun update(chatItem: ChatItem) {
        val db = writableDatabase
        db.update(
            TABLE_NAME,
            putValues(chatItem),
            "$COLUMN_ID = ?", arrayOf(chatItem.profile.uid)
        )
    }

    val all: List<ChatItem>
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
            return getChatList(cursor)
        }

    fun getById(id: String): ChatItem? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            getProjection,
            "$COLUMN_ID = ?", arrayOf(id),
            null,
            null,
            null
        )
        val chatList = getChatList(cursor)
        return if (chatList.isEmpty()) {
            null
        } else chatList[0]
    }

    fun deleteById(id: String) {
        val db = writableDatabase
        db.delete(
            TABLE_NAME,
            "$COLUMN_ID = ?", arrayOf(id)
        )
    }

    fun deleteAll() {
        val db = writableDatabase
        db.delete(
            TABLE_NAME,
            null,
            null
        )
    }

    companion object {
        private const val TABLE_NAME = "chat_list"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TIME = "time"
        private const val COLUMN_CONTENT = "content"
        private const val COLUMN_PROFILE = "profile"

        const val CREATE_CHATLIST = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " TEXT," +
                COLUMN_TIME + " TEXT," +
                COLUMN_CONTENT + " TEXT," +
                COLUMN_PROFILE + " TEXT)"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
        private val getProjection = arrayOf(
            COLUMN_ID,
            COLUMN_TIME,
            COLUMN_CONTENT,
            COLUMN_PROFILE
        )
    }
}