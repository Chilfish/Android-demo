package top.chilfish.labs.notepad.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

interface NoteDao {

    @Query("SELECT * FROM $NOTE_TABLE ORDER BY time DESC")
    suspend fun getAll(): Flow<List<NoteEntity>>

    @Insert
    suspend fun insert(note: NoteEntity): Long

    @Delete
    suspend fun delete(note: NoteEntity): Int

    @Update
    suspend fun update(note: NoteEntity): Int
}