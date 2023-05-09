package top.chilfish.compose.notepad

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NoteDetail(
    modifier: Modifier = Modifier,
    viewModel: NotepadViewModel,
    noteId: Long?
) {
    Text(
        text = "$noteId",
        modifier = modifier.padding(36.dp)
    )
}

@Composable
fun NoteDetailAppBar() {

}
