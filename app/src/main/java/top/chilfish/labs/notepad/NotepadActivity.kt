package top.chilfish.labs.notepad

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import top.chilfish.labs.MainApplication
import top.chilfish.labs.R
import top.chilfish.labs.databinding.ActivityNotepadBinding
import top.chilfish.labs.notepad.data.NoteEntity

class NotepadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotepadBinding
    private val noteAdapter = NoteAdapter()
    private lateinit var rv: RecyclerView

    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory((application as MainApplication).noteRepository)
    }

    private fun init() {
        binding = ActivityNotepadBinding.inflate(layoutInflater)

        rv = binding.notes
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = noteAdapter

        noteAdapter.addItems(
            listOf(
                NoteEntity(title = "title1", content = "content1"),
                NoteEntity(title = "title2", content = "content2"),
                NoteEntity(title = "title3", content = getString(R.string.place_long_content)),
            ).toMutableList()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setContentView(binding.root)
    }
}