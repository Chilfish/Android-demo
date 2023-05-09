package top.chilfish.labs

import android.content.Intent
import android.os.Bundle
import top.chilfish.labs.notepad.NotepadActivity
import top.chilfish.labs.sqlite.SqliteActivity

class MainActivity : BaseActivity() {
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