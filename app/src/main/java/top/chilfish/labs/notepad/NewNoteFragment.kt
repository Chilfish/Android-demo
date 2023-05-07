package top.chilfish.labs.notepad

import android.os.Bundle
import android.view.View
import top.chilfish.labs.BaseFragment
import top.chilfish.labs.R
import top.chilfish.labs.alert
import top.chilfish.labs.databinding.FragNewNoteBinding
import top.chilfish.labs.notepad.data.NoteEntity

class NewNoteFragment(
    private val noteViewModel: NoteViewModel,
    private val note: NoteEntity = NoteEntity(),
    private val isNew: Boolean = false,
) : BaseFragment<FragNewNoteBinding>() {
    override val layoutId = R.layout.frag_new_note

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = binding.root.context

        binding.note = note
        binding.noteTitle.setText(note.title)
        binding.noteContent.setText(note.content)

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.save_note -> {
                    val old = note.copy()

                    val title = binding.noteTitle.text.toString()
                    val content = binding.noteContent.text.toString()

                    note.title = title.ifEmpty { getString(R.string.new_title) }
                    note.content = content.ifEmpty { getString(R.string.new_content) }
                    note.time = System.currentTimeMillis()

                    if (isNew) noteViewModel.insert(note)
                    else noteViewModel.update(old, note)

                    back()
                    true
                }

                R.id.delete_note -> {
                    alert(
                        context = context,
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
                    true
                }

                else -> false
            }
        }

        binding.topAppBar.setNavigationOnClickListener { back() }
    }

    private fun back() {
        activity?.supportFragmentManager?.popBackStack()
    }
}