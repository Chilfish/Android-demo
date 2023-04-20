package top.chilfish.chatapp.adapter

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import top.chilfish.chatapp.R
import top.chilfish.chatapp.databinding.ItemChatBinding
import top.chilfish.chatapp.entity.ChatItem
import top.chilfish.chatapp.ui.activities.ChatMainActivity

class ChatListAdapter : BaseAdapter<ChatItem, ItemChatBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.item_chat
    }

    override fun onBindViewHolder(holder: ViewHolder<ItemChatBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        val data = items[position]
        val binding = holder.binding
        try {
            Glide.with(holder.itemView.context)
                .load(data.profile.avatar)
                .into(binding.chatAvatar)

            binding.chatName.text = data.profile.name
            binding.chatContent.text = data.content
            binding.chatTime.text = data.time
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onItemClicked(item: ChatItem, view: View) {
        val bundle = Bundle()
        bundle.putSerializable("profile", item.profile)
        val intent = Intent(view.context, ChatMainActivity::class.java)
        intent.putExtras(bundle)
        startActivity(view.context, intent, null)
    }
}