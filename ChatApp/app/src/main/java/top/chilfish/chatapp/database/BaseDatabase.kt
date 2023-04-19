package top.chilfish.chatapp.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

abstract class BaseDatabase : SQLiteOpenHelper {
    constructor(context: Context?, factory: CursorFactory?) : super(
        context,
        DB_NAME,
        factory,
        DB_VERSION
    )

    constructor(context: Context?) : super(context, DB_NAME, null, DB_VERSION)

    override fun onCreate(db: SQLiteDatabase) {
        db.beginTransaction()
        try {
            db.execSQL(ChatListDB.CREATE_CHATLIST)
            db.execSQL(MessageDB.CREATE_MESSAGE)
            db.execSQL(ContactDB.CREATE_CONTACT)
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, i: Int, i1: Int) {}

    companion object {
        const val DB_NAME = "chat-app.db"
        const val DB_VERSION = 1
    }
}