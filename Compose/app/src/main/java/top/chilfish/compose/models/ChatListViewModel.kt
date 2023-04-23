package top.chilfish.compose.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import top.chilfish.compose.Chat
import top.chilfish.compose.Profile

class ChatListViewModel : ViewModel() {
    private val _chats = MutableStateFlow<List<Chat>>(emptyList())
    val chats: StateFlow<List<Chat>> = _chats

    init {
        loadChats()
    }

    private fun loadChats() {
        viewModelScope.launch {
            // 模拟异步加载聊天列表
            delay(1000)
            _chats.value = listOf(
                Chat(
                    id = "1",
                    profile = Profile(
                        name = "Chat 1",
                        avatar = "https://p.chilfish.top/avatar.webp"
                    )
                ),
                Chat(
                    id = "2",
                    profile = Profile(
                        name = "Chat 2",
                        avatar = "https://p.chilfish.top/avatar1.webp"
                    )
                ),
                Chat(
                    id = "3",
                    profile = Profile(
                        name = "Chat 3",
                        avatar = "https://p.chilfish.top/avatar2.webp"
                    )
                ),
            )
        }
    }
}