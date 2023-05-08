package top.chilfish.compose.notepad

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import top.chilfish.compose.notepad.data.NoteEntity


@Composable
fun NotepadPage(
    modifier: Modifier = Modifier,
    viewModel: NotepadViewModel
) {
    val (notes, setNotes) = rememberSaveable {
        mutableStateOf(listOf<NoteEntity>())
    }

    LaunchedEffect(viewModel.noteState) {
        viewModel.noteState.collect {
            setNotes(it.Notes)
        }
    }

    LazyColumn(modifier = modifier) {
        items(notes.size) { index ->
            NotepadItem(notes[index])
        }
    }
}

@Composable
fun NotepadItem(note: NoteEntity) {
    Row() {
        Text(
            text = note.title,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = note.content,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}