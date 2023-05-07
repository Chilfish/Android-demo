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
    private val repository: NoteRepository,
    private val adapter: NoteAdapter
) : ViewModel() {
    private val _noteState = MutableStateFlow(NoteState())
    val noteState: StateFlow<NoteState> = _noteState

    init {
        loadNotes()
    }

    fun loadNotes() = viewModelScope.launch {
        repository.allNotes.collect {
            _noteState.value = NoteState(it.toMutableList())
            adapter.addItems(it.toMutableList())
        }
    }

    fun insert(note: NoteEntity) = viewModelScope.launch {
        repository.insert(note)
        adapter.addItem(note)
    }

    fun delete(note: NoteEntity) = viewModelScope.launch {
        repository.delete(note)
        adapter.rmItem(note)
    }

    fun update(old: NoteEntity, note: NoteEntity) = viewModelScope.launch {
        repository.update(note)
        adapter.updateItem(old, note)
    }
}

class NoteViewModelFactory(
    private val repository: NoteRepository,
    private val adapter: NoteAdapter
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(repository, adapter) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

data class NoteState(
    val notes: MutableList<NoteEntity> = mutableListOf()
)