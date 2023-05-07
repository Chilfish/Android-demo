package top.chilfish.labs.notepad

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import top.chilfish.labs.BaseActivity
import top.chilfish.labs.BaseAdapter
import top.chilfish.labs.MainApplication
import top.chilfish.labs.R
import top.chilfish.labs.databinding.ActivityNotepadBinding
import top.chilfish.labs.notepad.data.NoteEntity

class NotepadActivity : BaseActivity(), BaseAdapter.OnItemClickListener<NoteEntity> {
    private lateinit var binding: ActivityNotepadBinding
    private val noteAdapter = NoteAdapter()
    private lateinit var rv: RecyclerView

    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory((application as MainApplication).noteRepository, noteAdapter)
    }

    private fun init() {
        binding = ActivityNotepadBinding.inflate(layoutInflater)

        rv = binding.notes
        rv.layoutManager = LinearLayoutManager(this)
        noteAdapter.setOnItemClickListener(this)
        rv.adapter = noteAdapter

        noteViewModel.loadNotes()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setContentView(binding.root)

        lifecycleScope.launch {
            noteViewModel.noteState.collect {
                noteAdapter.updateItems(it.notes)
            }
        }

        binding.addNote.setOnClickListener {
            replaceFragment(
                NewNoteFragment(noteViewModel = noteViewModel, isNew = true),
                R.id.frag_newNote
            )
        }
    }

    override fun onItemClick(item: NoteEntity) {
        replaceFragment(NewNoteFragment(noteViewModel, item), R.id.frag_newNote)
    }
}