package top.chilfish.labs.notepad.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val NOTE_TABLE = "notes"

@Entity(tableName = NOTE_TABLE)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String = "",
    var content: String = "",
    var time: Long = System.currentTimeMillis()
) {
    @get:Ignore
    val formattedTime: String
        get() {
            val date = Date(time)
            val sdf = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
            return sdf.format(date)
        }
}
