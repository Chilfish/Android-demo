package top.chilfish.labs.gpt.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(
    entities = [MessageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GPTDatabase : RoomDatabase() {
    abstract fun dao(): GPTDao

    companion object {
        private const val NAME = "GPT.db"

        @Volatile
        private var INSTANCE: GPTDatabase? = null

        fun getDatabase(
            context: Context,
        ): GPTDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: databaseBuilder(
                    context.applicationContext,
                    GPTDatabase::class.java,
                    NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}