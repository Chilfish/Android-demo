package top.chilfish.compose.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import top.chilfish.compose.data.Message
import top.chilfish.compose.data.fake.Messages

class MessageViewModel(val chatUid: String) : ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    init {
        loadMessage()
    }

    private fun loadMessage() {
        viewModelScope.launch {
            _messages.value = Messages.chatHistory(chatUid)
        }
    }
}