package top.chilfish.chatapp.helper

import com.fasterxml.jackson.databind.ObjectMapper
import top.chilfish.chatapp.entity.ChatItem
import top.chilfish.chatapp.entity.Message
import top.chilfish.chatapp.entity.Profile
import top.chilfish.chatapp.entity.Profile.Companion.load

object JsonParser {

    @Throws(Exception::class)
    fun messages(json: String?): List<Message> {
        val objectMapper = ObjectMapper()
        val rootNode = objectMapper.readTree(json)
        val messages: MutableList<Message> = ArrayList()
        for (node in rootNode) {
            val receiverId = node["receiverId"].asText()
            val senderId = node["senderId"].asText()
            val content = node["content"].asText()
            val timestamp = node["timestamp"].asText()
            val id = node["id"].asText()
            val message = Message(
                content, receiverId, senderId,
                timestamp, id, senderId == load().uid
            )
            messages.add(message)
        }
        return messages
    }

    @Throws(Exception::class)
    fun chatList(json: String?): List<ChatItem> {
        val objectMapper = ObjectMapper()
        val rootNode = objectMapper.readTree(json)
        val chatItems: MutableList<ChatItem> = ArrayList()
        for (node in rootNode) {
            val uid = node["uid"].asText()
            val name = node["name"].asText()
            val avatar = node["avatar"].asText()
            val email = node["email"].asText()
            val bio = node["bio"].asText()
            val profile = Profile(uid, name, avatar, email, bio)
            val lastMessage = node["lastMessage"].asText()
            val lastMessageTime = TimeFormat.toString(
                node["lastTime"].asText().toLong(),
                "MM-dd"
            )
            val chatItem = ChatItem(profile, lastMessage, lastMessageTime)
            chatItems.add(chatItem)
        }
        return chatItems
    }

    @Throws(Exception::class)
    fun profile(json: String?): List<Profile> {
        val objectMapper = ObjectMapper()
        val rootNode = objectMapper.readTree(json)
        val profiles: MutableList<Profile> = ArrayList()
        for (node in rootNode) {
            val uid = node["uid"].asText()
            val name = node["name"].asText()
            val avatar = node["avatar"].asText()
            val email = node["email"].asText()
            val bio = node["bio"].asText()
            val profile = Profile(uid, name, avatar, email, bio)
            profiles.add(profile)
        }
        return profiles
    }
}