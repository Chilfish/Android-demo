package top.chilfish.labs.notepad.data

import android.util.Log
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import top.chilfish.labs.base.Diffable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val NOTE_TABLE = "notes"

@Entity(tableName = NOTE_TABLE)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var title: String = "",
    var content: String = "",
    var time: Long = System.currentTimeMillis()
) : Diffable {
    @get:Ignore
    val formattedTime: String
        get() {
            val date = Date(time)
            val sdf = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
            return sdf.format(date)
        }

    override fun itemId() = id

    override fun sameContent(other: Diffable) =
        other is NoteEntity &&
                title == other.title && content == other.content
}

fun NotesLog(items: MutableList<NoteEntity>, name: String = "") {
    var out = "notes:{"
    for (i in items) {
        out += "\n\t$i"
    }
    Log.d("note", "$name: $out}")
}