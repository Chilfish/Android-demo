package top.chilfish.labs

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import top.chilfish.labs.gpt.data.GPTDao
import top.chilfish.labs.gpt.data.GPTDatabase
import top.chilfish.labs.gpt.data.GPTRepository
import top.chilfish.labs.module.ApplicationScope
import top.chilfish.labs.module.IODispatcher
import top.chilfish.labs.notepad.data.NoteDatabase
import top.chilfish.labs.notepad.data.NoteRepository
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {
    private val noteDatabase by lazy { NoteDatabase.getDatabase(this, applicationScope) }
    val noteRepository by lazy { NoteRepository(noteDatabase.noteDao()) }

    @Inject
    @ApplicationScope
    lateinit var applicationScope: CoroutineScope

    @Inject
    @IODispatcher
    lateinit var ioDispatcher: CoroutineDispatcher

    @Inject
    lateinit var gptDB: GPTDatabase

    @Inject
    lateinit var gptRepo: GPTRepository
}