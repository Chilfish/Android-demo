package top.chilfish.compose.data

data class ChatItem(
    val id: String = "0",
    val profile: Profile = Profile(),
    val content: String = "Hello",
    val time: String = "12:00",
)