package top.chilfish.compose.notepad

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import top.chilfish.compose.R
import top.chilfish.compose.notepad.data.NoteEntity
import top.chilfish.compose.notepad.data.NoteSaver

@Composable
fun NoteDetail(
    modifier: Modifier = Modifier,
    viewModel: NotepadViewModel,
    noteId: Long?
) {
    val noteFound = remember {
        mutableStateOf(viewModel.findNoteById(noteId!!))
    }

    Scaffold(
        topBar = { NoteDetailAppBar(viewModel) },
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) { padding ->
        NoteDetailContent(
            modifier = Modifier.padding(padding),
            viewModel = viewModel,
            noteFound = noteFound.value
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailAppBar(
    viewModel: NotepadViewModel
) {
    val modifier = Modifier.padding(horizontal = 4.dp)
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = { viewModel.processEvent(NoteEvent.BackToHome) },
                modifier = modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = modifier
                )
            }
        },
        actions = {
            IconButton(
                onClick = { viewModel.processEvent(NoteEvent.SaveNote) },
                modifier = modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = stringResource(R.string.check)
                )
            }

            IconButton(
                onClick = { viewModel.processEvent(NoteEvent.DeleteNote) },
                modifier = modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(R.string.delete)
                )
            }
        },
        title = {},
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.surface)
    )
}

@Composable
fun NoteDetailContent(
    modifier: Modifier = Modifier,
    viewModel: NotepadViewModel,
    noteFound: NoteEntity
) {
    val (note, setNote) = rememberSaveable(stateSaver = NoteSaver) { mutableStateOf(noteFound) }

    LaunchedEffect(note) {
//        Log.d("Notepad", "note: $note")
        viewModel.processEvent(NoteEvent.NoteChanged(note))
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextArea(
            value = note.title,
            valueStyle = MaterialTheme.typography.titleLarge,
            valueWeight = FontWeight.Bold,
            setValue = { setNote.invoke(note.copy(title = it)) },
            placeholder = stringResource(R.string.label_title)
        )

        Text(
            text = note.formattedTime,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(8.dp),

            )

        TextArea(
            value = note.content,
            valueStyle = MaterialTheme.typography.bodyLarge,
            setValue = { setNote.invoke(note.copy(content = it)) },
            placeholder = stringResource(R.string.start_typing)
        )
    }
}

@Composable
fun TextArea(
    modifier: Modifier = Modifier,
    value: String,
    valueStyle: TextStyle,
    valueWeight: FontWeight? = null,
    placeholder: String = "",
    setValue: (String) -> Unit
) {
    Box(modifier = modifier) {
        BasicTextField(
            value = value,
            onValueChange = setValue,
            textStyle = TextStyle(
                color = valueStyle.color,
                fontWeight = valueWeight ?: valueStyle.fontWeight,
                fontSize = valueStyle.fontSize
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )
        if (value.isEmpty()) {
            Text(
                text = placeholder,
                color = Color.Gray,
                modifier = Modifier.padding(start = 12.dp, top = 12.dp),
                style = valueStyle,
                fontWeight = valueStyle.fontWeight
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextAreaP() {
    TextArea(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        value = "",
        valueStyle = MaterialTheme.typography.titleLarge,
        placeholder = "Placeholder",
        setValue = {}
    )
}