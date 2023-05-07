package top.chilfish.labs.notepad.data

import androidx.room.Entity
import androidx.room.PrimaryKey

const val NOTE_TABLE = "notes"

@Entity(tableName = NOTE_TABLE)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val time: String = System.currentTimeMillis().toString()
)
