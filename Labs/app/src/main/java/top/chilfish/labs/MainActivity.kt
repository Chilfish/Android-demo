package top.chilfish.labs

import android.content.Intent
import android.os.Bundle
import coil.load
import top.chilfish.labs.base.BaseActivity
import top.chilfish.labs.databinding.ActivityMainBinding
import top.chilfish.labs.music.MusicActivity
import top.chilfish.labs.notepad.NotepadActivity
import top.chilfish.labs.sms.SMSActivity
import top.chilfish.labs.sqlite.SqliteActivity
import top.chilfish.labs.utils.ContextHolder

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    private fun init() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ContextHolder.init(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

        binding.btn.setOnClickListener {
            val url = binding.urlEdit.text.toString()
            binding.image.load(url)
        }

        val sqlite = SqliteActivity::class.java
        val notepad = NotepadActivity::class.java
        val sms = SMSActivity::class.java
        val music = MusicActivity::class.java

        startActivity(Intent(this, music))
        finish()
    }
}

