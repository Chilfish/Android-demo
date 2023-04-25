package top.chilfish.compose.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import top.chilfish.compose.data.Message

class MessageViewModel : ViewModel() {
    private val _message = MutableStateFlow<List<Message>>(emptyList())
    val message: StateFlow<List<Message>> = _message

    init {
        loadMessage()
    }

    private fun loadMessage() {
        viewModelScope.launch {
            _message.value = listOf(
                Message(
                    id = "1",
                    content = "Hello",
                    isSelf = true
                ),
                Message(
                    content = "Hello",
                    isSelf = false
                ),
                Message(
                    content = "Hello",
                    isSelf = true
                ),
            )
        }
    }
}