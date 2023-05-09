package top.chilfish.compose.notepad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import top.chilfish.compose.notepad.data.NEW_NOTE_ID
import top.chilfish.compose.notepad.data.NoteEntity
import top.chilfish.compose.notepad.data.NotepadRepository
import top.chilfish.compose.notepad.navigation.NavigationActions
import top.chilfish.compose.notepad.navigation.Routers

class NotepadViewModel(
    private val repository: NotepadRepository,
) : ViewModel() {
    private val _noteState = MutableStateFlow(NoteState())
    val noteState: Flow<NoteState> = _noteState
        .asStateFlow()
        .distinctUntilChanged { old, new ->
            old.Notes == new.Notes
        }

    lateinit var navController: NavHostController

    init {
        load()
    }

    private fun load() = viewModelScope.launch {
        repository.allNotes.collect {
            _noteState.value = _noteState.value.copy(Notes = it)
        }
    }

    fun processEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.ToDetail -> toDetail(event.note)
            is NoteEvent.ToNewNote -> toNewNote()
        }
    }

    private fun toDetail(note: NoteEntity) {
        NavigationActions(navController).navigateTo(Routers.Detail, note.id)
    }

    private fun toNewNote() {
        NavigationActions(navController).navigateTo(Routers.Detail, NEW_NOTE_ID)
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

sealed class NoteEvent {
    data class ToDetail(val note: NoteEntity) : NoteEvent()
    object ToNewNote : NoteEvent()
}