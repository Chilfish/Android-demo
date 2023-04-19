package top.chilfish.chatapp.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import top.chilfish.chatapp.Main
import top.chilfish.chatapp.R
import top.chilfish.chatapp.database.MessageDB
import top.chilfish.chatapp.entity.Message
import top.chilfish.chatapp.entity.Profile
import top.chilfish.chatapp.ui.fragments.ChatBarFragment
import top.chilfish.chatapp.ui.fragments.MessageFragment
import java.util.UUID

class ChatMainActivity : AppCompatActivity() {
    private val tag = "ChatMainActivity"

    private var messageList: MutableList<Message>? = null
    private var mMessageInput: EditText? = null
    private var mSendButton: ImageButton? = null
    private var mMessageFragment: MessageFragment? = null

    private var curUid: String? = null
    private var chatUid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_main)
        val chatBundle = intent.extras
        if (chatBundle == null) {
            Log.d(tag, "Bundle is null")
            return
        }
        val profile: Profile?
        try {
            profile = chatBundle.getSerializable("profile") as Profile?
            Log.d(tag, "profile: " + profile.toString())
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }
        chatUid = profile!!.uid
        curUid = Main.AppCONTEXT
            ?.getSharedPreferences("profile", MODE_PRIVATE)
            ?.getString("uid", "")
        loadMessage()

        mMessageFragment = messageList?.let { MessageFragment(it) }
        mSendButton = findViewById(R.id.btn_send)
        mMessageInput = findViewById(R.id.chat_input)
        replaceFragment(ChatBarFragment(profile), R.id.frag_chat_bar)
        replaceFragment(mMessageFragment!!, R.id.frag_messages)
        sendMessage()
    }

    private fun replaceFragment(fragment: Fragment, id: Int) {
        supportFragmentManager.beginTransaction()
            .replace(id, fragment)
            .commit()
    }

    private fun loadMessage() {
        try {
            MessageDB(this).use { db -> messageList = db.getById(chatUid) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //  TODO: add send message to server and local storage, meanwhile update the chat list
    private fun sendMessage() {
        mSendButton!!.setOnClickListener {
            val message = mMessageInput!!.text.toString()
            if (message.isEmpty()) {
                return@setOnClickListener
            }
            val newMessage = chatUid?.let { it1 ->
                curUid?.let { it2 ->
                    Message(
                        message,
                        it1, it2, UUID.randomUUID().toString()
                    )
                }
            }
            mMessageFragment!!.onSendMessage(this, newMessage)
            mMessageInput!!.setText("")
        }
    }

    interface OnSendMessage {
        fun onSendMessage(context: Context?, message: Message?)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
}