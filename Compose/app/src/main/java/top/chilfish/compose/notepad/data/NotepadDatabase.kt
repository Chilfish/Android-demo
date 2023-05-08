package top.chilfish.compose.notepad.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [NoteEntity::class], version = 1)
abstract class NotepadDatabase : RoomDatabase() {
    abstract fun notepadDao(): NotepadDao

    companion object {
        private const val DB_NAME = "notes.db"

        @Volatile
        private var INSTANT: NotepadDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): NotepadDatabase {
            return INSTANT ?: synchronized(this) {
                val instant = Room.databaseBuilder(
                    context.applicationContext,
                    NotepadDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(NotepadDatabaseCallback(scope))
                    .build()
                INSTANT = instant
                instant
            }
        }

        private class NotepadDatabaseCallback(
            private val scope: CoroutineScope
        ) : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANT?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.notepadDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(noteDao: NotepadDao) {
            val longText = "Material Design\n" +
                    "Material 3 is the latest version of Google’s open-source design system." +
                    " Design and build beautiful, usable products with Material 3."

            noteDao.deleteAll()
            noteDao.insert(NoteEntity(title = "Title 1", content = "Content 1"))
            noteDao.insert(NoteEntity(title = "Title 2", content = longText))
        }
    }
}