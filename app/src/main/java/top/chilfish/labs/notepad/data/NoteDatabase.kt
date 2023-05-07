package top.chilfish.labs.notepad.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        private const val DB_NAME = "note.db"

        @Volatile
        private var INSTANT: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            return INSTANT ?: synchronized(this) {
                val instant = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANT = instant
                instant
            }
        }
    }
}