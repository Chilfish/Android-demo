package top.chilfish.labs.sqlite

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import top.chilfish.labs.databinding.ActivitySqliteBinding
import top.chilfish.labs.showToast

class SqliteActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySqliteBinding

    private val contactAdapter = ContactAdapter()

    private lateinit var rv: RecyclerView

    private lateinit var nameInput: TextInputEditText
    private lateinit var phoneInput: TextInputEditText

    private fun init() {
        binding = ActivitySqliteBinding.inflate(layoutInflater)

        nameInput = binding.nameEdit
        phoneInput = binding.phoneEdit
        rv = binding.contacts

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = contactAdapter

        SQLEvents.init(this)
        findAll()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setContentView(binding.root)

        binding.query.setOnClickListener { query() }
        binding.insert.setOnClickListener { insert() }
        binding.all.setOnClickListener { findAll() }
    }

    private fun checkInput(): Contact? {
        val name = nameInput.text.toString().trim().replace('\n', ' ')
        val phone = phoneInput.text.toString().trim().replace('\n', ' ')

        if (name.isEmpty() && phone.isEmpty()) {
            showToast("name and phone is empty")
            return null
        }
        return Contact(name = name, phone = phone)

    }

    private fun checkEmpty(name: String, phone: String): Boolean {
        if (name.isEmpty()) {
            showToast("name is empty")
            return false
        }
        if (phone.isEmpty()) {
            showToast("phone is empty")
            return false
        }
        return true
    }

    private fun insert() {
        val contact = checkInput() ?: return
        if (!checkEmpty(contact.name, contact.phone))
            return

        val res = SQLEvents.insert(contact)
        if (res == MesCode.SUCCESS) {
            contactAdapter.addItem(contact)
        }
    }

    private fun query() {
        val contact = checkInput() ?: return
        val res = SQLEvents.query(contact) ?: return

        Log.d("SQLITE", "query: $res")
        contactAdapter.updateItems(res)
    }

    private fun findAll() {
        val res = SQLEvents.getALl() ?: return
        Log.d("SQLITE", "findAll: $res")
        contactAdapter.updateItems(res)
    }
}