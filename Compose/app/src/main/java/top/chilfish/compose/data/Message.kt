package top.chilfish.compose.data

import top.chilfish.compose.provider.curUid

data class Message(
    val senderId: String = "1",
    val receiverId: String = "0",
    val content: String = "",
    val timestamp: String = "",
) {
    val isSelf: Boolean
        get() = senderId == curUid
}