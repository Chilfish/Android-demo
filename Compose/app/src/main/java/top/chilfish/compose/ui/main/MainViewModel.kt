package top.chilfish.compose.ui.main

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import top.chilfish.compose.notepad.NotepadActivity
import top.chilfish.compose.ui.room.RoomActivity

class MainViewModel : ViewModel() {
    fun jumpToTest(context: Context) {
        val intent = Intent(context, RoomActivity::class.java)
        context.startActivity(intent)
    }

    fun jumpToNotepad(context: Context) {
        val intent = Intent(context, NotepadActivity::class.java)
        context.startActivity(intent)
    }
}