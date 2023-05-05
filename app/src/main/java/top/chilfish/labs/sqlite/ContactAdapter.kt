package top.chilfish.labs.sqlite

import androidx.recyclerview.widget.DiffUtil
import top.chilfish.labs.BaseAdapter
import top.chilfish.labs.BaseDiffCallback
import top.chilfish.labs.R
import top.chilfish.labs.alert
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

        binding.delete.setOnClickListener {
            alert(
                context = context,
                title = context.getString(R.string.alert_title_delete),
                subtitle = context.getString(R.string.alert_subtitle_delete),
                cancel = context.getString(R.string.cancel),
                confirm = context.getString(R.string.confirm),
                cancelAction = {},
                confirmAction = { deleteEvent(data) }
            ).show()
        }

        binding.update.setOnClickListener { updateEvent(data) }
    }

    private fun deleteEvent(data: Contact) {
        if (SQLEvents.delete(data) == MesCode.SUCCESS) {
            rmItem(data)
            showToast(context.getString(R.string.toast_success, "delete"))
        } else {
            showToast(context.getString(R.string.toast_failed, "delete"))
        }
    }

    private fun updateEvent(data: Contact) {
        nameInput.setText(data.name)
        phoneInput.setText(data.phone)
        if (!isUpdateClicked) {
            toggleVisible()
            isUpdateClicked = true
        }

        cancelBtn.setOnClickListener { cancelUpdateEvent() }
        confirmBtn.setOnClickListener { confirmUpdateEvent(data) }
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
            showToast(context.getString(R.string.toast_success, "update"))
        } else {
            showToast(context.getString(R.string.toast_failed, "update"))
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
