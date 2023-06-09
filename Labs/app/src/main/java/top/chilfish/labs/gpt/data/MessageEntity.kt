package top.chilfish.labs.gpt.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import top.chilfish.labs.base.Diffable
import top.chilfish.labs.utils.formattedTime

const val GPT_Table = "messages"

@Serializable
@Entity(tableName = GPT_Table)
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val time: Long = System.currentTimeMillis(),
    val content: String = "",
    val isMe: Boolean = true,
) : Diffable {
    val timeString: String
        get() = formattedTime(time, "MM-dd hh:mm")

    override fun itemId() = time
    override fun sameContent(other: Diffable) =
        other is MessageEntity && time == other.time && content == other.content && isMe == other.isMe
}
