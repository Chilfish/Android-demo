package top.chilfish.labs.gpt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
        launch { getSetting() }

        repo.allMessages.collect { messages ->
            _uiState.update {
                it.copy(messages = messages.toMutableList())
            }
        }
    }

    fun send(content: String) = viewModelScope.launch(ioDispatcher) {
        val message = MessageEntity(content = content)
        val lastMessage = _uiState.value.messages.last()
        val mess = listOf(lastMessage.toChat(), message.toChat())

        _uiState.update {
            it.copy(messages = it.messages.apply { add(message) })
        }
        repo.insert(message)

        val (_, key, host) = _uiState.value
        val resMessage = repo.send(key, host, mess) ?: return@launch

        val botMessage = MessageEntity(
            content = resMessage.content,
            role = resMessage.role
        )
        repo.insert(botMessage)
    }

    fun saveSetting(newKey: String, newHost: String) = viewModelScope.launch {
        _uiState.update { it.copy(key = newKey, bastHost = newHost) }
        SettingsProvider.saveSettings(newKey, newHost)
    }

    private suspend fun getSetting() {
        val (key, host) = SettingsProvider.getSettings()
        _uiState.update { it.copy(key = key, bastHost = host) }
    }

    fun deleteAll() = viewModelScope.launch(ioDispatcher) { repo.deleteAll() }
}

data class UIState(
    val messages: MutableList<MessageEntity> = mutableListOf(),

    val key: String = "",
    val bastHost: String = "https://api.openai.com",
)