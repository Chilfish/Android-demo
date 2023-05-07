package top.chilfish.labs

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import top.chilfish.labs.notepad.data.NoteDatabase
import top.chilfish.labs.notepad.data.NoteRepository

class MainApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    private val noteDatabase by lazy { NoteDatabase.getDatabase(this) }
    val noteRepository by lazy { NoteRepository(noteDatabase.noteDao()) }
}