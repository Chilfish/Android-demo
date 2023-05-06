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
    suspend fun insertUser(user: UserEntity): Long

    @Delete
    suspend fun deleteUser(user: UserEntity): Int

    @Update
    suspend fun updateUser(user: UserEntity): Int

    @Query("SELECT * FROM user ORDER BY name")
    fun queryAllUser(): Flow<List<UserEntity>>

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun queryUserById(id: Int): UserEntity

    @Query("SELECT * FROM user WHERE name like :name")
    suspend fun queryUserByName(name: String): UserEntity

    @Query("SELECT * FROM user WHERE age like :age")
    suspend fun queryUserByAge(age: Int): UserEntity
}