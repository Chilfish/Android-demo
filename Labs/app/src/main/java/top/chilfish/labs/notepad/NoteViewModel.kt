package top.chilfish.labs.notepad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import top.chilfish.labs.notepad.data.NoteEntity
import top.chilfish.labs.notepad.data.NoteRepository
import top.chilfish.labs.notepad.data.NotesLog

class NoteViewModel(
    private val repository: NoteRepository,
) : ViewModel() {
    private val _noteState = MutableStateFlow(NoteState())
    val noteState: Flow<NoteState> = _noteState
        .asStateFlow()
        .distinctUntilChanged { old, new ->
            old.notes == new.notes
        }

    init {
        loadNotes()
    }

     private fun loadNotes() = viewModelScope.launch {
        repository.allNotes.collect {
            _noteState.value = _noteState.value.copy(notes = it)
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

    fun setSelected(note: NoteEntity) = viewModelScope.launch {
        _noteState.value = _noteState.value.copy(selected = note)
    }

    fun getSelected() = _noteState.value.selected.copy()
}

class NoteViewModelFactory(
    private val repository: NoteRepository,
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
    val notes: MutableList<NoteEntity> = mutableListOf(),
    val selected: NoteEntity = NoteEntity()
)