package top.chilfish.labs.sqlite

import android.content.Context
import android.util.Log
import top.chilfish.labs.showToast

object SQLEvents {
    private lateinit var db: ContactDB

    fun init(context: Context) {
        this.db = ContactDB(context)
    }

    fun insert(contact: Contact): MesCode {
        val res = db.insert(contact)
        showCode(res)
        return res
    }

    fun query(contact: Contact): MutableList<Contact>? {
//        Log.d("SQLITE", "query: $contact")

        val res = if (contact.name.isEmpty()) {
            db.findByPhone(contact.phone)
        } else if (contact.phone.isEmpty()) {
            db.findByName(contact.name)
        } else {
            db.find(contact)
        }

        showToast("find ${res?.size} items")
        return res
    }

    fun delete(contact: Contact): MesCode {
        return db.delById(contact.id)
    }

    fun update(old: Contact, new: Contact): MesCode {
        return db.update(old, new)
    }

    fun getALl(): MutableList<Contact>? {
        return db.getContacts()
    }
}