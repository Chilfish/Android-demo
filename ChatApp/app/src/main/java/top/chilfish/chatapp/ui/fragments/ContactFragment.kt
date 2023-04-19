package top.chilfish.chatapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import top.chilfish.chatapp.R
import top.chilfish.chatapp.adapter.ContactAdapter
import top.chilfish.chatapp.databinding.FragmentContactsBinding
import top.chilfish.chatapp.entity.Profile

class ContactFragment(private val mContactList: List<Profile>) :
    BaseFragment<FragmentContactsBinding?>() {
    override val layoutId: Int
        get() = R.layout.fragment_contacts

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = view.context
        val recyclerView = view as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = ContactAdapter()
        adapter.updateItems(mContactList)
        recyclerView.adapter = adapter
    }
}