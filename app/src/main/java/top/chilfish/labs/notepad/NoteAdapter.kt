package top.chilfish.labs.notepad

import androidx.recyclerview.widget.DiffUtil
import top.chilfish.labs.BaseAdapter
import top.chilfish.labs.BaseDiffCallback
import top.chilfish.labs.R
import top.chilfish.labs.databinding.ItemNoteBinding
import top.chilfish.labs.notepad.data.NoteEntity

class NoteAdapter : BaseAdapter<NoteEntity, ItemNoteBinding>() {
    override val itemLayout = R.layout.item_note

    override fun updateItems(newItems: MutableList<NoteEntity>) {
        DiffUtil
            .calculateDiff(NoteDiff(items, newItems))
            .dispatchUpdatesTo(this)
        items.clear()
        items.addAll(newItems)
    }

    override fun onBindViewHolder(holder: ViewHolder<ItemNoteBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        val data = getItem(position)
        binding.note = data
    }
}

class NoteDiff(
    oldItems: MutableList<NoteEntity>,
    newItems: MutableList<NoteEntity>
) : BaseDiffCallback<NoteEntity>(oldItems, newItems) {
    override fun areSameItems(oldItem: NoteEntity, newItem: NoteEntity) = oldItem.id == newItem.id
    override fun areSameContent(oldItem: NoteEntity, newItem: NoteEntity) = oldItem == newItem
}
