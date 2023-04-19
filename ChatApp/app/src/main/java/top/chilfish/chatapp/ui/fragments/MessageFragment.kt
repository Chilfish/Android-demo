package top.chilfish.chatapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import top.chilfish.chatapp.R
import top.chilfish.chatapp.adapter.MessageAdapter
import top.chilfish.chatapp.databinding.FragmentMessageListBinding
import top.chilfish.chatapp.entity.Message
import top.chilfish.chatapp.ui.activities.ChatMainActivity.OnSendMessage

class MessageFragment(private val mMessages: MutableList<Message>) :
    BaseFragment<FragmentMessageListBinding?>(), OnSendMessage {
    private var recyclerView: RecyclerView? = null
    private var adapter: MessageAdapter? = null

    override val layoutId: Int
        get() = R.layout.fragment_message_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = view.context
        recyclerView = view as RecyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        adapter = MessageAdapter()
        adapter!!.updateItems(mMessages)

        recyclerView!!.adapter = adapter
        recyclerView!!.scrollToPosition(mMessages.size - 1)
    }

    override fun onSendMessage(context: Context?, message: Message?) {
        message?.let { mMessages.add(it) }
        adapter!!.notifyItemChanged(mMessages.size - 1)
        recyclerView!!.scrollToPosition(mMessages.size - 1)
    }
}