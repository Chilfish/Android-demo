package top.chilfish.labs.sqlite

import android.content.Context
import android.util.Log
import top.chilfish.labs.showToast

object SQLEvents {
    private lateinit var db: ContactDB

    fun init(context: Context) {
        this.db = ContactDB(context)
    }

    fun query(contact: Contact): MutableList<Contact>? {
        val res = if (contact.name.isEmpty()) {
            db.findByPhone(contact.phone)
        } else if (contact.phone.isEmpty()) {
            db.findByName(contact.name)
        } else {
            db.find(contact)
        }
        return res
    }

    fun insert(contact: Contact) = db.insert(contact)

    fun delete(contact: Contact) = db.delete(contact.id)

    fun update(old: Contact, new: Contact) = db.update(old, new)

    fun getALl() = db.getContacts()
}