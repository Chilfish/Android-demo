package top.chilfish.labs.gpt

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GPTViewModel(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {
    private var _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState

    fun send(content: String) {
        val message = Message(content = content)
        _uiState.update {
            it.copy(messages = it.messages.apply { add(message) })
        }
        Log.d("GPT", "messages: ${uiState.value.messages}")
    }
}

data class UIState(
    val messages: MutableList<Message> = mutableListOf(),
)