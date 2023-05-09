package top.chilfish.labs.notepad.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM $NOTE_TABLE ORDER BY time DESC")
    fun getAll(): Flow<MutableList<NoteEntity>>

    @Insert
    suspend fun insert(note: NoteEntity): Long

    @Delete
    suspend fun delete(note: NoteEntity): Int

    @Query("DELETE FROM $NOTE_TABLE")
    suspend fun deleteAll()

    @Update
    suspend fun update(note: NoteEntity): Int
}