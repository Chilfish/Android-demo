package top.chilfish.labs.gpt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import top.chilfish.labs.gpt.data.ChatMessage
import top.chilfish.labs.gpt.data.GPTRepository
import top.chilfish.labs.gpt.data.MessageEntity
import top.chilfish.labs.module.IODispatcher
import javax.inject.Inject

@HiltViewModel
class GPTViewModel @Inject constructor(
    private val repo: GPTRepository,

    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private var _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState

    init {
        load()
    }

    private fun load() = viewModelScope.launch(ioDispatcher) {
        repo.allMessages.collect { messages ->
            _uiState.update {
                it.copy(messages = messages.toMutableList())
            }
        }
    }

    fun send(content: String) = viewModelScope.launch(ioDispatcher) {
        val message = MessageEntity(content = content)
        _uiState.update {
            it.copy(messages = it.messages.apply { add(message) })
        }
        repo.insert(message)

        val mess = listOf(ChatMessage(content))
        repo.send(mess)
//        Log.d("GPT", "messages: ${json.encodeToString(mess)}, $mess")
    }
}

data class UIState(
    val messages: MutableList<MessageEntity> = mutableListOf(),
)