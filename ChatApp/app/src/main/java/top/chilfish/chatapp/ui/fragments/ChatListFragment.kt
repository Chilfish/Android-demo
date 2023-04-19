package top.chilfish.chatapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import top.chilfish.chatapp.R
import top.chilfish.chatapp.adapter.ChatListAdapter
import top.chilfish.chatapp.databinding.FragmentChatListsBinding
import top.chilfish.chatapp.entity.ChatItem

class ChatListFragment(private val mChats: List<ChatItem>) :
    BaseFragment<FragmentChatListsBinding?>() {

    override val layoutId: Int
        get() = R.layout.fragment_chat_lists


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = view.context
        val recyclerView = view as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = ChatListAdapter()
        adapter.updateItems(mChats)
        recyclerView.adapter = adapter
    }
}