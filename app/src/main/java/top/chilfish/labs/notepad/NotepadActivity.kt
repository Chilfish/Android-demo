package top.chilfish.labs.notepad

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import top.chilfish.labs.MainApplication
import top.chilfish.labs.R
import top.chilfish.labs.databinding.ActivityNotepadBinding

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
            replaceFragment(NewNoteFragment(), R.id.frag_newNote)
        }
    }

    private fun replaceFragment(fragment: Fragment, id: Int) {
        supportFragmentManager.beginTransaction()
            .replace(id, fragment)
            .addToBackStack(null)
            .commit()
    }
}