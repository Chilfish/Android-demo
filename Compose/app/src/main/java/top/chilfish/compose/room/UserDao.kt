package top.chilfish.compose.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: UserEntity): Long

    @Delete
    suspend fun delete(user: UserEntity): Int

    @Update
    suspend fun update(user: UserEntity): Int

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM $TABLE_NAME ORDER BY name")
    fun queryAll(): Flow<List<UserEntity>>

    @Query("SELECT * FROM $TABLE_NAME WHERE id = :id")
    suspend fun queryById(id: Long): UserEntity?

    @Query("SELECT * FROM $TABLE_NAME WHERE name like :name")
    suspend fun queryByName(name: String): UserEntity?

    @Query("SELECT * FROM $TABLE_NAME WHERE age like :age")
    suspend fun queryByAge(age: Int): UserEntity?
}