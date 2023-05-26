package top.chilfish.labs.sms

import top.chilfish.labs.BaseAdapter
import top.chilfish.labs.R
import top.chilfish.labs.databinding.ItemSmsBinding

class SMSAdapter :
    BaseAdapter<SMSEntity, ItemSmsBinding>(R.layout.item_sms) {
    override fun onBindViewHolder(holder: ViewHolder<ItemSmsBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        val data = getItem(position)
        binding.sms = data
    }
}