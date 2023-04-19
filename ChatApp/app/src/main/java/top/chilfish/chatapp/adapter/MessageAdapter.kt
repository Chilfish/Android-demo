package top.chilfish.chatapp.adapter

import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import top.chilfish.chatapp.R
import top.chilfish.chatapp.databinding.ItemMessageBinding
import top.chilfish.chatapp.entity.Message
import top.chilfish.chatapp.helper.TimeFormat

class MessageAdapter : BaseAdapter<Message, ItemMessageBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.item_message
    }

    override fun onBindViewHolder(holder: ViewHolder<ItemMessageBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        val data = items[position]
        val binding = holder.binding

        binding.messText.text = data.content
        binding.messTime.text = TimeFormat.toString(data.time, "HH:mm")
        binding.timeBubble.text =
            TimeFormat.toString(data.time, "MM-dd HH:mm")
        val layoutParams = binding.messBody.layoutParams as LinearLayout.LayoutParams
        if (data.isRight) {
            layoutParams.gravity = Gravity.END
            binding.messBody.setBackgroundResource(R.drawable.message_r)
        } else {
            layoutParams.gravity = Gravity.START
            binding.messBody.setBackgroundResource(R.drawable.message_l)
        }

        // 时间泡泡：第一条消息要加、否则与上一条消息的时间间隔大于5分钟才加
        if (items[0] == data) {
            binding.timeBubble.visibility = View.VISIBLE
            return
        }
        val index = items.indexOf(data)
        val lastMessage = items[index - 1]
        if (data.time - lastMessage.time >= 5 * 60 * 1000) {
            binding.timeBubble.visibility = View.VISIBLE
        } else {
            binding.timeBubble.visibility = View.GONE
        }
    }

}