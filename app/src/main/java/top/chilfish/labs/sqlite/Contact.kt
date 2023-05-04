package top.chilfish.labs.sqlite

import android.util.Log

data class Contact(
    var id: Int = 0,
    var name: String = "Default Name",
    var phone: String = "12345678901"
)

fun logContacts(title: String, contacts: MutableList<Contact>) {
    var logs = "$title: {"
    contacts.forEach {
        logs += "\n\tid: ${it.id}, name: ${it.name}, phone: ${it.phone};"
    }
    Log.d("SQLITE", "$logs }\n")
}