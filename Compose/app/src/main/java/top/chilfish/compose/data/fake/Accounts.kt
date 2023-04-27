package top.chilfish.compose.data.fake

import top.chilfish.compose.data.Profile
import top.chilfish.compose.models.currUid

object Accounts {
    private val accounts = listOf(
        Profile(
            uid = "0",
            name = "Chilfish",
            avatar = "https://p.chilfish.top/avatar.webp",
            bio = "A developer who loves to code",
            email = "chill4fish@gamil.com"
        ),
        Profile(
            uid = "1",
            name = "Organic Fish",
            avatar = "https://p.chilfish.top/avatar0.webp",
            bio = "A fish who loves to code",
            email = "a@chilfish.top"
        ),
        Profile(
            uid = "2",
            name = "Fishy Fish",
            avatar = "https://p.chilfish.top/avatar1.webp",
            bio = "A fish who loves to code",
            email = "b@chilfish.top"
        )
    )

    val contacts = accounts.filter { it.uid != currUid.value }

    val find: (String) -> Profile = { uid ->
        accounts.find { it.uid == uid } ?: Profile()
    }
}