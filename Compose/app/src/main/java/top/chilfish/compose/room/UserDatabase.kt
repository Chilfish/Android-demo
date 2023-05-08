package top.chilfish.compose.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private const val DATABASE_NAME = "user.db"

        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    UserDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(UserDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class UserDatabaseCallback(
            private val scope: CoroutineScope
        ) : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { userDatabase ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(userDatabase.userDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(userDao: UserDao) {
            userDao.deleteAll()
            userDao.insert(UserEntity(name = "Tom", age = 18))
            userDao.insert(UserEntity(name = "Jerry", age = 19))
            userDao.insert(UserEntity(name = "Jack", age = 20))
        }
    }
}
