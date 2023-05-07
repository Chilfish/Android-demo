package top.chilfish.labs.notepad

import android.os.Bundle
import android.view.View
import top.chilfish.labs.BaseFragment
import top.chilfish.labs.R
import top.chilfish.labs.databinding.FragNewNoteBinding

class NewNoteFragment : BaseFragment<FragNewNoteBinding>() {
    override val layoutId = R.layout.frag_new_note

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = view.context
    }
}