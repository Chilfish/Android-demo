package top.chilfish.labs.sqlite

import top.chilfish.labs.BaseAdapter
import top.chilfish.labs.R
import top.chilfish.labs.databinding.ItemContactBinding
import top.chilfish.labs.showToast

class ContactAdapter : BaseAdapter<Contact, ItemContactBinding>() {
    override val itemId = R.layout.item_contact

    override fun onBindViewHolder(holder: ViewHolder<ItemContactBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        val data = getItem(position)
        binding.contact = data

        binding.delete.setOnClickListener {
            if (SQLEvents.delete(data) == MesCode.SUCCESS) {
                rmItem(data)
                showToast("delete success")
            } else {
                showToast("delete failed")
            }
        }

//        binding.update.setOnClickListener { view ->
//            val item = getItem(view.tag as Int)
//            val new = Contact(
//                id = item.id,
//                name = binding.name.text.toString(),
//                phone = binding.phone.text.toString()
//            )
//            if (SQLEvents.update(item, new) == MesCode.SUCCESS) {
//                showToast("update success")
//                items = items.map {
//                    if (it.id == item.id) new
//                    else it
//                }
//                updateItems(items)
//            } else {
//                showToast("update failed")
//            }
//        }
    }
}
