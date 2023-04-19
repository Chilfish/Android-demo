package top.chilfish.chatapp.api

import android.content.Context
import top.chilfish.chatapp.database.ChatListDB
import top.chilfish.chatapp.database.ContactDB
import top.chilfish.chatapp.database.MessageDB
import top.chilfish.chatapp.entity.ChatItem
import top.chilfish.chatapp.entity.Message
import top.chilfish.chatapp.entity.Profile
import top.chilfish.chatapp.helper.JsonParser
import top.chilfish.chatapp.helper.LoadFile

object FetchData {

    private var messages: List<Message>? = null
    private var chatItems: List<ChatItem>? = null
    private var contacts: List<Profile>? = null
    fun messages() {
        try {
            val json = LoadFile.assetsString("messages.json")
            messages = JsonParser.messages(json)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun chatList() {
        try {
            val json = LoadFile.assetsString("chatList.json")
            chatItems = JsonParser.chatList(json)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun contacts() {
        try {
            val json = LoadFile.assetsString("contacts.json")
            contacts = JsonParser.profile(json)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun all() {
        chatList()
        contacts()
        messages()
    }

    fun saveToDB(context: Context?) {
        try {
            val messageDB = MessageDB(context)
            for (message in messages!!) {
                messageDB.insert(message)
            }

            val chatListDB = ChatListDB(context)
            for (chatItem in chatItems!!) {
                chatListDB.insert(chatItem)
            }

            val contactDB = ContactDB(context)
            for (contact in contacts!!) {
                contactDB.insert(contact)
            }

            messageDB.close()
            chatListDB.close()
            contactDB.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}