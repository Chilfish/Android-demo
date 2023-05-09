package top.chilfish.compose.notepad

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import top.chilfish.compose.BaseActivity
import top.chilfish.compose.ChatApplication
import top.chilfish.compose.notepad.navigation.NoteNavHost

class NotepadActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val notepadViewModel = ViewModelProvider(
            this,
            NotepadViewModelFactory((application as ChatApplication).notepadRepository)
        )[NotepadViewModel::class.java]

        setContent {
            NoteNavHost(viewModel = notepadViewModel)
        }
    }
}
