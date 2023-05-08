package top.chilfish.compose.notepad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import top.chilfish.compose.notepad.data.NoteEntity
import top.chilfish.compose.notepad.data.NotepadRepository

class NotepadViewModel(
    private val repository: NotepadRepository
) : ViewModel() {
    private val _noteState = MutableStateFlow(NoteState())
    val noteState: Flow<NoteState> = _noteState
        .asStateFlow()
        .distinctUntilChanged { old, new ->
            old.Notes == new.Notes
        }

    init {
        load()
    }

    fun load() = viewModelScope.launch {
        repository.allNotes.collect {
            _noteState.value = _noteState.value.copy(Notes = it)
        }
    }
}

class NotepadViewModelFactory(
    private val repository: NotepadRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotepadViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotepadViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


data class NoteState(
    val Notes: MutableList<NoteEntity> = mutableListOf(),
)