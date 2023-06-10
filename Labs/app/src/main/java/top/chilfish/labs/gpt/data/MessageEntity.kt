package top.chilfish.labs.gpt.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Required
import kotlinx.serialization.Serializable
import top.chilfish.labs.utils.formattedTime

const val GPT_Table = "messages"

@Serializable
@Entity(tableName = GPT_Table)
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val time: Long = System.currentTimeMillis(),
    val isMe: Boolean = true,
    val content: String = "",
    val role: String = Role.user,
) {
    val timeString: String
        get() = formattedTime(time, "MM-dd HH:mm")

    fun toChat() = ChatMessage(content, role)
}

@Serializable
data class ChatMessage(
    val content: String = "",
    val role: String = Role.user,
)

@Serializable
data class RequestBody(
    val model: String = "gpt-3.5-turbo",
    val messages: List<ChatMessage> = listOf(),
)

object Role {
    @Required
    const val user: String = "user"
    const val assistant: String = "assistant"
}