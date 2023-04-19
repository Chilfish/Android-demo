package top.chilfish.chatapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import top.chilfish.chatapp.entity.Message
import top.chilfish.chatapp.entity.Profile.Companion.load

class MessageDB(context: Context?) : BaseDatabase(context) {
    private fun putValues(message: Message): ContentValues {
        val values = ContentValues()
        values.put(COLUMN_ID, message.id)
        values.put(COLUMN_CONTENT, message.content)
        values.put(COLUMN_RECEIVER_ID, message.receiverId)
        values.put(COLUMN_SENDER_ID, message.senderId)
        values.put(COLUMN_TIME, message.timeString)
        values.put(COLUMN_RIGHT, message.isRightString)
        return values
    }

    private fun getMessages(cursor: Cursor): MutableList<Message> {
        val messages: MutableList<Message> = ArrayList()
        while (cursor.moveToNext()) {
            val message = Message()
            message.content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
            message.receiverId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECEIVER_ID))
            message.senderId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SENDER_ID))
            message.setTime(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME)))
            message.id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID))
            message.setRight(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RIGHT)))
            messages.add(message)
        }
        cursor.close()
        return messages
    }

    fun insert(message: Message) {
        val db = writableDatabase
        db.insert(
            TABLE_NAME,
            null,
            putValues(message)
        )
    }

    fun update(message: Message) {
        val db = writableDatabase
        val selection = "$COLUMN_ID = ?"
        val args = arrayOf(message.id)
        db.update(
            TABLE_NAME,
            putValues(message),
            selection,
            args
        )
    }

    val all: MutableList<Message>
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
            return getMessages(cursor)
        }

    // Get messages by idA and idB
    fun getById(id: String?): MutableList<Message> {
        val curId = load().uid
        val db = readableDatabase
        val selection = COLUMN_RECEIVER_ID + " = ? AND " + COLUMN_SENDER_ID + " = ?" +
                " OR " + ("$COLUMN_SENDER_ID = ? AND $COLUMN_RECEIVER_ID = ?")
        val args = arrayOf(id, curId, id, curId)
        val cursor = db.query(
            TABLE_NAME,
            getProjection,
            selection,
            args,
            null,
            null,
            null
        )
        return getMessages(cursor)
    }

    fun delete(message: Message): Boolean {
        val db = writableDatabase
        val selection = "$COLUMN_ID = ?"
        val args = arrayOf(message.id)
        val result = db.delete(
            TABLE_NAME,
            selection,
            args
        )
        return result > 0
    }

    companion object {
        private const val TABLE_NAME = "messages"
        private const val COLUMN_ID = "id"
        private const val COLUMN_CONTENT = "content"
        private const val COLUMN_RECEIVER_ID = "receiver_id"
        private const val COLUMN_SENDER_ID = "sender_id"
        private const val COLUMN_TIME = "time"
        private const val COLUMN_RIGHT = "isRight"
        const val CREATE_MESSAGE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " TEXT ," +
                COLUMN_CONTENT + " TEXT," +
                COLUMN_RECEIVER_ID + " TEXT," +
                COLUMN_SENDER_ID + " TEXT," +
                COLUMN_TIME + " TEXT," +
                COLUMN_RIGHT + " TEXT)"
        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
        private val getProjection = arrayOf(
            COLUMN_ID,
            COLUMN_CONTENT,
            COLUMN_RECEIVER_ID,
            COLUMN_SENDER_ID,
            COLUMN_TIME,
            COLUMN_RIGHT
        )
    }
}