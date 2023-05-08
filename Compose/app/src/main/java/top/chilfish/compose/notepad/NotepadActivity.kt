package top.chilfish.compose.notepad

import android.os.Bundle
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import top.chilfish.compose.BaseActivity
import top.chilfish.compose.ChatApplication
import top.chilfish.compose.ui.room.RoomPage

class NotepadActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val notepadViewModel = ViewModelProvider(
            this,
            NotepadViewModelFactory((application as ChatApplication).notepadRepository)
        )[NotepadViewModel::class.java]

        setContent {
            Scaffold { padding ->
                NotepadPage(
                    modifier = Modifier.padding(padding),
                    viewModel = notepadViewModel
                )
            }

        }
    }
}