package top.chilfish.compose.notepad.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val NOTE_TABLE_NAME = "note"

@Entity(tableName = NOTE_TABLE_NAME)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String = "",
    val content: String = "",
    val createTime: Long = System.currentTimeMillis(),
) {
    @get:Ignore
    val formattedTime: String
        get() {
            val date = Date(createTime)
            val sdf = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
            return sdf.format(date)
        }
}