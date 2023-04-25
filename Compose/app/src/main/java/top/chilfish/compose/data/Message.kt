package top.chilfish.compose.data

data class Message(
    val id: String = "0",
    val senderId: String = "1",
    val receiverId: String = "0",
    val content: String = "Hello",
    val time: String = "12:00",
    val isSelf: Boolean = false,
)