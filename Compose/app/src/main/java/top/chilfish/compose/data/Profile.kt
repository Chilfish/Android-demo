package top.chilfish.compose.data

data class Profile(
    val avatar: String = "https://p.chilfish.top/avatar.webp",
    val uid: String = "1",
    val name: String = "Default Name",
    val bio: String = "Default Bio",
    val email: String = "Default Email",
)