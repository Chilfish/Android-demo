package top.chilfish.labs.notepad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import top.chilfish.labs.notepad.data.NoteEntity
import top.chilfish.labs.notepad.data.NoteRepository

class NoteViewModel(
    private val repository: NoteRepository
) : ViewModel() {
    private val _noteState = MutableStateFlow(NoteState())
    val noteState: StateFlow<NoteState> = _noteState

    init {
        loadNotes()
    }

    fun loadNotes() = viewModelScope.launch {
        repository.getAll().collect {
            _noteState.value = NoteState(it)
        }
    }

    fun insert(note: NoteEntity) = viewModelScope.launch {
        repository.insert(note)
    }

    fun delete(note: NoteEntity) = viewModelScope.launch {
        repository.delete(note)
    }

    fun update(note: NoteEntity) = viewModelScope.launch {
        repository.update(note)
    }
}

data class NoteState(
    val notes: List<NoteEntity> = emptyList(),
)