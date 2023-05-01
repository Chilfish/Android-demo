package top.chilfish.compose.data.fake

import top.chilfish.compose.data.Profile
import top.chilfish.compose.data.ProfileProp
import top.chilfish.compose.provider.curUid

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

    val contacts = accounts.filter { it.uid != curUid }

    fun find(value: String, prop: ProfileProp = ProfileProp.UID) =
        accounts.find { profile ->
            when (prop) {
                ProfileProp.UID -> profile.uid == value
                ProfileProp.NAME -> profile.name == value
                ProfileProp.AVATAR -> profile.avatar == value
                ProfileProp.BIO -> profile.bio == value
                ProfileProp.EMAIL -> profile.email == value
            }
        }
}