package top.chilfish.compose.notepad

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import top.chilfish.compose.R
import top.chilfish.compose.notepad.data.NEW_NOTE_ID
import top.chilfish.compose.notepad.data.NoteEntity
import top.chilfish.compose.notepad.data.NotepadRepository
import top.chilfish.compose.notepad.navigation.NavigationActions
import top.chilfish.compose.notepad.navigation.Routers
import top.chilfish.compose.utils.ContextHolder

class NotepadViewModel(
    private val repository: NotepadRepository?,
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
        withContext(Dispatchers.IO) {
            repository?.allNotes
                ?.collect {
                    _noteState.value = _noteState.value.copy(Notes = it)
                }
        }
    }

    fun processEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.NoteChanged -> noteChanged(event.note)
            is NoteEvent.ToDetail -> toDetail(event.note)

            is NoteEvent.ToNewNote -> toNewNote()
            is NoteEvent.BackToHome -> backToHome()
            is NoteEvent.SaveNote -> saveNote()
            is NoteEvent.DeleteNote -> deleteNote()
        }
    }

    private fun toDetail(note: NoteEntity) {
        Log.d("Notepad", "toDetail: $note")
        noteChanged(note)
        NavigationActions(navController).navigateTo(Routers.Detail, note.id)
    }

    private fun toNewNote() {
        noteChanged(NoteEntity(id = NEW_NOTE_ID))
        NavigationActions(navController).navigateTo(Routers.Detail, NEW_NOTE_ID)
    }

    private fun backToHome() {
        NavigationActions(navController).navigateTo(Routers.Home)
    }

    private fun noteChanged(note: NoteEntity) {
        _noteState.value = _noteState.value.copy(selectedNote = note)
    }

    private fun saveNote() = viewModelScope.launch {
        val selectedNote = _noteState.value.selectedNote
        Log.d("Notepad", "saveNote: $selectedNote")

        withContext(Dispatchers.IO) {
            if (selectedNote.id == NEW_NOTE_ID) {
                repository?.insert(selectedNote)
            } else {
                repository?.update(NoteEntity.update(selectedNote))
            }
        }
        backToHome()
    }

    private fun deleteNote() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository?.delete(_noteState.value.selectedNote)
        }
        backToHome()
    }

    fun findNoteById(id: Long): NoteEntity {
        var res = _noteState.value.Notes.find { it.id == id }
        if (res == null || id == NEW_NOTE_ID) {
            res = NoteEntity(
                id = NEW_NOTE_ID,
                title = ContextHolder.context.getString(R.string.new_title),
                content = ContextHolder.context.getString(R.string.new_content),
            )
        }
        _noteState.value = _noteState.value.copy(selectedNote = res)
        Log.d("Notepad", "findNoteById: $res")
        return res
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
    val selectedNote: NoteEntity = NoteEntity(),
)

sealed class NoteEvent {
    data class ToDetail(val note: NoteEntity) : NoteEvent()
    data class NoteChanged(val note: NoteEntity) : NoteEvent()
    object ToNewNote : NoteEvent()
    object BackToHome : NoteEvent()
    object SaveNote : NoteEvent()
    object DeleteNote : NoteEvent()
}