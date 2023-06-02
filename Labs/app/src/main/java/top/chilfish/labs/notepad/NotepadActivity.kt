package top.chilfish.labs.notepad

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import top.chilfish.labs.base.BaseActivity
import top.chilfish.labs.base.BaseAdapter
import top.chilfish.labs.MainApplication
import top.chilfish.labs.R
import top.chilfish.labs.databinding.ActivityNotepadBinding
import top.chilfish.labs.notepad.data.NoteEntity

class NotepadActivity : BaseActivity(), BaseAdapter.OnItemClickListener<NoteEntity> {
    private lateinit var binding: ActivityNotepadBinding
    private val noteAdapter = NoteAdapter()
    private lateinit var rv: RecyclerView

    private lateinit var noteViewModel: NoteViewModel

    private fun init() {
        binding = ActivityNotepadBinding.inflate(layoutInflater)
        noteViewModel = ViewModelProvider(
            this,
            NoteViewModelFactory((application as MainApplication).noteRepository)
        )[NoteViewModel::class.java]

        rv = binding.notes
        rv.layoutManager = LinearLayoutManager(this)
        noteAdapter.setOnItemClickListener(this)
        rv.adapter = noteAdapter
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
            replaceFragment(NewNoteFragment(isNew = true), R.id.frag_newNote)
        }
    }

    override fun onItemClick(item: NoteEntity) {
        noteViewModel.setSelected(item)
        Log.d("note", "Activity: $item")
        replaceFragment(NewNoteFragment(), R.id.frag_newNote)
    }
}