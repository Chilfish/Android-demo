package top.chilfish.compose.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import top.chilfish.compose.data.ChatItem
import top.chilfish.compose.data.Profile

class ChatListViewModel : ViewModel() {
    private val _chats = MutableStateFlow<List<ChatItem>>(emptyList())
    val chats: StateFlow<List<ChatItem>> = _chats

    init {
        loadChats()
    }

    private fun loadChats() {
        viewModelScope.launch {
            _chats.value = listOf(
                ChatItem(
                    id = "1",
                    profile = Profile(
                        name = "Chat 1",
                        avatar = "https://p.chilfish.top/avatar.webp"
                    )
                ),
                ChatItem(
                    id = "2",
                    profile = Profile(
                        name = "Chat 2",
                        avatar = "https://p.chilfish.top/avatar1.webp"
                    )
                ),
                ChatItem(
                    id = "3",
                    profile = Profile(
                        name = "Chat 3",
                        avatar = "https://p.chilfish.top/avatar2.webp"
                    )
                ),
            )
        }
    }

    fun onChatSelected(chatId: String, navController: NavController) {
        println(chatId)
    }
}