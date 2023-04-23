package top.chilfish.compose

data class Profile(
    val avatar: String = "https://p.chilfish.top/avatar.webp",
    val uid: String = "1",
    val name: String = "Default Name",
    val bio: String = "Default Bio",
    val email: String = "Default Email",
)

data class Chat(
    val id: String = "0",
    val profile: Profile = Profile(),
    val content: String = "Hello",
    val time: String = "12:00",
)

data class Message(
    val id: String = "0",
    val senderId: String = "1",
    val receiverId: String = "0",
    val content: String = "Hello",
    val time: String = "12:00",
    val isSelf: Boolean = false,
)