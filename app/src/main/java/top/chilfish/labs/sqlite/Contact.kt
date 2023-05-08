package top.chilfish.labs.sqlite

import android.util.Log
import top.chilfish.labs.Diffable

data class Contact(
    var id: Long = 0,
    var name: String = "Default Name",
    var phone: String = "12345678901"
) : Diffable {
    override fun itemId() = id

    override fun sameContent(other: Diffable) =
        other is Contact &&
                name == other.name && phone == other.phone
}

fun logContacts(title: String, contacts: MutableList<Contact>) {
    var logs = "$title: {"
    contacts.forEach {
        logs += "\n\tid: ${it.id}, name: ${it.name}, phone: ${it.phone};"
    }
    Log.d("SQLITE", "$logs }\n")
}