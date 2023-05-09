package top.chilfish.compose.notepad.data

import androidx.compose.runtime.saveable.listSaver
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val NOTE_TABLE_NAME = "note"
const val NEW_NOTE_ID = 0L

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

    companion object {
        fun update(note: NoteEntity) = NoteEntity(
            id = note.id,
            title = note.title,
            content = note.content,
        )
    }
}

val NoteSaver = listSaver<NoteEntity, Any>(
    save = {
        listOf(it.id, it.title, it.content, it.createTime)
    },
    restore = {
        NoteEntity(
            id = it[0] as Long,
            title = it[1] as String,
            content = it[2] as String,
            createTime = it[3] as Long,
        )
    }
)