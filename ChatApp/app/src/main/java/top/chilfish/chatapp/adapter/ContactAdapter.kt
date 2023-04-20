package top.chilfish.chatapp.adapter

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import top.chilfish.chatapp.R
import top.chilfish.chatapp.databinding.ItemContactBinding
import top.chilfish.chatapp.entity.Profile
import top.chilfish.chatapp.ui.activities.ProfileActivity

class ContactAdapter : BaseAdapter<Profile, ItemContactBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.item_contact
    }

    override fun onBindViewHolder(holder: ViewHolder<ItemContactBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        val data = items[position]
        val binding = holder.binding
        try {
            Glide.with(holder.itemView.context)
                .load(data.avatar)
                .into(binding.contactAvatar)

            binding.contactName.text = data.name
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onItemClicked(item: Profile, view: View) {
        val bundle = Bundle()
        bundle.putSerializable("profile", item)
        val intent = Intent(view.context, ProfileActivity::class.java)
        intent.putExtras(bundle)
        startActivity(view.context, intent, null)
    }
}