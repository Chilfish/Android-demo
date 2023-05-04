package top.chilfish.labs.sqlite

import androidx.recyclerview.widget.DiffUtil
import top.chilfish.labs.BaseAdapter
import top.chilfish.labs.BaseDiffCallback
import top.chilfish.labs.R
import top.chilfish.labs.databinding.ItemContactBinding
import top.chilfish.labs.showToast
import top.chilfish.labs.sqlite.ViewProvider.cancelBtn
import top.chilfish.labs.sqlite.ViewProvider.clearInput
import top.chilfish.labs.sqlite.ViewProvider.confirmBtn
import top.chilfish.labs.sqlite.ViewProvider.nameInput
import top.chilfish.labs.sqlite.ViewProvider.phoneInput
import top.chilfish.labs.sqlite.ViewProvider.toggleVisible

class ContactAdapter : BaseAdapter<Contact, ItemContactBinding>() {

    override val itemLayout = R.layout.item_contact
    private var isUpdateClicked = false

    override fun updateItems(newItems: MutableList<Contact>) {
        DiffUtil
            .calculateDiff(ContactDiff(items, newItems))
            .dispatchUpdatesTo(this)
        items.clear()
        items.addAll(newItems)
    }

    override fun onBindViewHolder(holder: ViewHolder<ItemContactBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        val data = getItem(position)
        binding.contact = data

        cancelBtn.setOnClickListener { cancelUpdateEvent() }

        binding.delete.setOnClickListener {
            if (SQLEvents.delete(data) == MesCode.SUCCESS) {
                rmItem(data)
                showToast("delete success")
            } else {
                showToast("delete failed")
            }
        }

        binding.update.setOnClickListener {
            nameInput.setText(data.name)
            phoneInput.setText(data.phone)
            if (!isUpdateClicked) {
                toggleVisible()
                isUpdateClicked = true
            }
            confirmBtn.setOnClickListener { confirmUpdateEvent(data) }
        }
    }

    private fun cancelUpdateEvent() {
        toggleVisible()
        clearInput()
        isUpdateClicked = false
    }

    private fun confirmUpdateEvent(data: Contact) {
        val new = Contact(
            id = data.id,
            name = nameInput.text.toString(),
            phone = phoneInput.text.toString()
        )

        if (SQLEvents.update(data, new) == MesCode.SUCCESS) {
            updateItem(data, new)
            showToast("update success")
        } else {
            showToast("update failed")
        }
        toggleVisible()
        clearInput()
        isUpdateClicked = false
    }
}

class ContactDiff(
    oldList: MutableList<Contact>,
    newList: MutableList<Contact>
) : BaseDiffCallback<Contact>(oldList, newList) {
    override fun areSameItems(oldItem: Contact, newItem: Contact) = oldItem.id == newItem.id
    override fun areSameContent(oldItem: Contact, newItem: Contact) = oldItem == newItem
}
