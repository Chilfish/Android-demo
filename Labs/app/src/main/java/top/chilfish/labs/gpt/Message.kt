package top.chilfish.labs.gpt

import kotlinx.serialization.Serializable
import top.chilfish.labs.base.Diffable
import top.chilfish.labs.utils.formattedTime

@Serializable
data class Message(
    val time: Long = System.currentTimeMillis(),
    val content: String = "",
    val isMe: Boolean = true,
) : Diffable {
    val timeString: String
        get() = formattedTime(time, "MM-dd hh:mm")

    override fun itemId() = time
    override fun sameContent(other: Diffable) =
        other is Message && time == other.time && content == other.content && isMe == other.isMe
}
