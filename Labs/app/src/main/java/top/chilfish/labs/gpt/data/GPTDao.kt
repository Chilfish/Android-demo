package top.chilfish.labs.gpt.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GPTDao {
    @Query("SELECT * FROM $GPT_Table ORDER BY time")
    fun getAll(): Flow<List<MessageEntity>>

    @Insert
    suspend fun insert(vararg message: MessageEntity)

    @Query("DELETE FROM $GPT_Table")
    suspend fun deleteAll()
}