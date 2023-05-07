package top.chilfish.labs.notepad

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import top.chilfish.labs.databinding.ActivityNotepadBinding
import top.chilfish.labs.notepad.data.NoteDatabase
import top.chilfish.labs.notepad.data.NoteRepository

class NotepadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotepadBinding
    private val noteAdapter = NoteAdapter()
    private lateinit var rv: RecyclerView

    private lateinit var noteViewModel: NoteViewModel

    private fun init() {
        binding = ActivityNotepadBinding.inflate(layoutInflater)

        rv = binding.notes
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = noteAdapter

        val database by lazy { NoteDatabase.getDatabase(this) }
        noteViewModel = NoteViewModel(NoteRepository(database.noteDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setContentView(binding.root)
    }
}