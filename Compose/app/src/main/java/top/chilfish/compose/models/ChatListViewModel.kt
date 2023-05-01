package top.chilfish.compose.models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import top.chilfish.compose.data.ChatItem
import top.chilfish.compose.data.fake.ChatList
import top.chilfish.compose.navigation.NavigationActions
import top.chilfish.compose.navigation.Routers

class ChatListViewModel : ViewModel() {
    private val _chats = MutableStateFlow<List<ChatItem>>(emptyList())
    val chats: StateFlow<List<ChatItem>> = _chats

    init {
        loadChats()
    }

    private fun loadChats() {
        viewModelScope.launch {
            ChatList.getChatList().collect() {
                _chats.value = it
            }
        }
    }

    fun onChatSelected(chatId: String, navController: NavHostController) {
        NavigationActions(navController).navigateTo(Routers.Message, chatId)
    }

}