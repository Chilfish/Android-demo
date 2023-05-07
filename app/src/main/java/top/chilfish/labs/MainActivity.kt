package top.chilfish.labs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import top.chilfish.labs.databinding.ActivitySqliteBinding
import top.chilfish.labs.notepad.NotepadActivity
import top.chilfish.labs.sqlite.Contact
import top.chilfish.labs.sqlite.ContactAdapter
import top.chilfish.labs.sqlite.SqliteActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ContextHolder.init(application)

        val sqlite = Intent(this, SqliteActivity::class.java)
        val notepad = Intent(this, NotepadActivity::class.java)
        startActivity(notepad)
        finish()
    }
}