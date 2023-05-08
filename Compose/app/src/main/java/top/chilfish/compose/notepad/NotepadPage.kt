package top.chilfish.compose.notepad

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import top.chilfish.compose.R
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

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        items(notes.size) { index ->
            NotepadItem(notes[index],
                onClick = {
                    viewModel.toDetail(notes[index])
                })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.title_notepad),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}

@Composable
fun FAB(
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = Modifier.padding(16.dp),
        onClick = onClick
    ) {
        Icon(Icons.Default.Add, stringResource(R.string.add))
    }
}

@Composable
fun NotepadItem(
    note: NoteEntity,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp)),
        shadowElevation = 12.dp,
        color = MaterialTheme.colorScheme.surface,
        onClick = { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.formattedTime,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(backgroundColor = 0xFFFFFFFF)
@Composable
fun NotepadPreview() {
    val notes = listOf(
        NoteEntity(
            title = "Title 1",
            content = "Content 1",
        ),
        NoteEntity(
            title = "Title 2",
            content = "Content 2",
        ),
        NoteEntity(
            title = "Title 3",
            content = "Content 3",
        ),
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        items(notes.size) { index ->
            NotepadItem(notes[index])
        }
    }
}