package top.chilfish.labs.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ContactDB(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.beginTransaction()
        try {
            db.execSQL(CREATE_CONTACT)
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL(DROP_CONTACT)
        onCreate(db)
    }

    companion object {
        const val DB_NAME = "contact.db"
        const val DB_VERSION = 1

        const val TABLE_NAME = "contact"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PHONE = "phone"

        const val CREATE_CONTACT = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_PHONE TEXT" +
                ")"

        const val DROP_CONTACT = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    private fun putValues(contact: Contact): ContentValues {
        val values = ContentValues()
        values.put(COLUMN_NAME, contact.name)
        values.put(COLUMN_PHONE, contact.phone)
        return values
    }

    private fun getContacts(cursor: Cursor): MutableList<Contact>? {
        val contacts: MutableList<Contact> = ArrayList()
        while (cursor.moveToNext()) {
            val contact = Contact()
            contact.id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            contact.name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            contact.phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
            contacts.add(contact)
        }
        cursor.close()
        return if (contacts.size == 0) null else contacts
    }

    fun insert(contact: Contact): MesCode {
        val found = findByName(contact.name, "=")?.get(0)
        if (found != null && found.name == contact.name) {
            return MesCode.NAME_EXIST
        }

        val db = writableDatabase
        val result = db.insert(
            TABLE_NAME,
            null,
            putValues(contact)
        )
        return if (result != -1L) MesCode.SUCCESS else MesCode.DB_FAIL
    }

    fun update(old: Contact, new: Contact): MesCode {
        if (find(old) == null) {
            return MesCode.CONTACT_NOT_EXIST
        }

        val db = writableDatabase
        val selection = "$COLUMN_ID=?"
        val args = arrayOf(old.id.toString())
        val result = db.update(
            TABLE_NAME,
            putValues(new),
            selection,
            args
        )
        return if (result > 0) MesCode.SUCCESS else MesCode.DB_FAIL
    }

    fun delete(id: Int): MesCode {
        val db = writableDatabase
        val selection = "$COLUMN_ID=?"
        val args = arrayOf(id.toString())
        val result = db.delete(
            TABLE_NAME,
            selection,
            args
        )
        return if (result > 0) MesCode.SUCCESS else MesCode.DB_FAIL
    }

    fun getContacts(): MutableList<Contact>? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            COLUMN_NAME
        )
        return getContacts(cursor)
    }

    fun find(contact: Contact, type: String = "like"): MutableList<Contact>? {
        val db = readableDatabase
        val selection = "$COLUMN_NAME $type ? and $COLUMN_PHONE $type ?"
        val args = arrayOf(contact.name, contact.phone)
        val cursor = db.query(
            TABLE_NAME,
            null,
            selection,
            args,
            null,
            null,
            COLUMN_NAME
        )
        return getContacts(cursor)
    }

    // fuzzy query
    fun findByName(name: String, type: String = "like"): MutableList<Contact>? =
        find(name, COLUMN_NAME, type)

    fun findByPhone(email: String, type: String = "like"): MutableList<Contact>? =
        find(email, COLUMN_PHONE, type)

    private fun find(key: String, col: String, type: String = "like"): MutableList<Contact>? {
        val k =
            if (type == "like") "%$key%"
            else key

        val db = readableDatabase
        val selection = "$col $type ?"
        val args = arrayOf(k)
        val cursor = db.query(
            TABLE_NAME,
            null,
            selection,
            args,
            null,
            null,
            COLUMN_NAME
        )
        return getContacts(cursor)
    }
}