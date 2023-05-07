package top.chilfish.labs.notepad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
        repository.allNotes.collect {
            _noteState.value = NoteState(it.toMutableList())
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

class NoteViewModelFactory(
    private val repository: NoteRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

data class NoteState(
    val notes: MutableList<NoteEntity> = mutableListOf()
)