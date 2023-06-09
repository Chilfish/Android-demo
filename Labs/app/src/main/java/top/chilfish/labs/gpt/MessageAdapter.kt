package top.chilfish.labs.gpt

import top.chilfish.labs.R
import top.chilfish.labs.base.BaseAdapter
import top.chilfish.labs.databinding.ItemMessageBinding

class MessageAdapter :
    BaseAdapter<Message, ItemMessageBinding>(R.layout.item_message) {

    override fun onBindViewHolder(holder: ViewHolder<ItemMessageBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        val data = getItem(position)
        binding.message = data
    }
}