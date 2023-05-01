package top.chilfish.compose.data.fake

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import top.chilfish.compose.data.ChatItem

object ChatList {
    fun getChatList(): Flow<List<ChatItem>> =
        flow {
            delay(500)
            emit(chatList)
        }

    private val chatList = listOf(
        Messages.lastChat("1"),
        Messages.lastChat("2"),
    )
}