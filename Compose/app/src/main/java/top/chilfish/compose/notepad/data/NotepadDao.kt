package top.chilfish.compose.notepad.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NotepadDao {
    @Query("SELECT * FROM $NOTE_TABLE_NAME ORDER BY createTime DESC")
    fun queryAll(): Flow<MutableList<NoteEntity>>

    @Update
    suspend fun update(note: NoteEntity)

    @Insert
    suspend fun insert(note: NoteEntity)

    @Delete
    suspend fun delete(note: NoteEntity)

    @Query("DELETE FROM $NOTE_TABLE_NAME")
    suspend fun deleteAll()
}