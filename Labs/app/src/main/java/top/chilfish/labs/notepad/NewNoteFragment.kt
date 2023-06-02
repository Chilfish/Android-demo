package top.chilfish.labs.notepad

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import top.chilfish.labs.base.BaseFragment
import top.chilfish.labs.R
import top.chilfish.labs.utils.alert
import top.chilfish.labs.databinding.FragNewNoteBinding
import top.chilfish.labs.notepad.data.NoteEntity

class NewNoteFragment(
    private val isNew: Boolean = false,
) : BaseFragment<FragNewNoteBinding>() {
    override val layoutId = R.layout.frag_new_note

    private lateinit var note: NoteEntity
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        noteViewModel = ViewModelProvider(requireActivity())[NoteViewModel::class.java]
        note = if (!isNew) noteViewModel.getSelected() else NoteEntity()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNote()

        binding.topAppBar.setOnMenuItemClickListener { onMenuClick(it) }
        binding.topAppBar.setNavigationOnClickListener { back() }
    }

    private fun onMenuClick(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.save_note -> {
                val title = binding.noteTitle.text.toString()
                val content = binding.noteContent.text.toString()

                note.title = title.ifEmpty { getString(R.string.new_title) }
                note.content = content.ifEmpty { getString(R.string.new_content) }
                note.time = System.currentTimeMillis()

                if (isNew) noteViewModel.insert(note)
                else noteViewModel.update(note)

                back()
                return true
            }

            R.id.delete_note -> {
                if (isNew) return true
                alert(
                    context = fragContext,
                    title = getString(R.string.alert_title_delete),
                    subtitle = getString(R.string.alert_subtitle_delete),
                    cancel = getString(R.string.cancel),
                    confirm = getString(R.string.confirm),
                    cancelAction = {},
                    confirmAction = {
                        noteViewModel.delete(note)
                        back()
                    }
                ).show()
                return true
            }

            else -> return false
        }
    }

    private fun back() {
        activity?.supportFragmentManager?.popBackStack()
    }

    private fun setNote() {
        binding.noteTitle.setText(note.title)
        binding.noteContent.setText(note.content)
        binding.noteTime.text = note.formattedTime
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        noteViewModel.setSelected(note.copy())
    }
}