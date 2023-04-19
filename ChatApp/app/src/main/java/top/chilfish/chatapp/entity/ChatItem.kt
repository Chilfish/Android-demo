package top.chilfish.chatapp.entity

import top.chilfish.chatapp.entity.Profile.Companion.getJson

class ChatItem(val profile: Profile, var content: String, var time: String) {
    val profileJson: String = getJson(profile)
}