package top.chilfish.labs.notepad

import top.chilfish.labs.base.BaseAdapter
import top.chilfish.labs.R
import top.chilfish.labs.databinding.ItemNoteBinding
import top.chilfish.labs.notepad.data.NoteEntity

class NoteAdapter :
    BaseAdapter<NoteEntity, ItemNoteBinding>(R.layout.item_note) {

    override fun onBindViewHolder(holder: ViewHolder<ItemNoteBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        val data = getItem(position)
        binding.note = data
    }
}