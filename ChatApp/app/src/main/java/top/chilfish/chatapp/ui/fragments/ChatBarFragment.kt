package top.chilfish.chatapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import top.chilfish.chatapp.R
import top.chilfish.chatapp.databinding.FragmentChatBarBinding
import top.chilfish.chatapp.entity.Profile
import top.chilfish.chatapp.ui.activities.ProfileActivity

class ChatBarFragment(val profile: Profile) :
    BaseFragment<FragmentChatBarBinding>() {

    override val layoutId = R.layout.fragment_chat_bar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.chatBarName?.text = profile.name

        val avatarView = binding?.chatAvatar!!
        Glide.with(view.context)
            .load(profile.avatar)
            .into(avatarView)

        avatarView.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("profile", profile)
            val intent = Intent(view.context, ProfileActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        binding?.btnChatBack?.setOnClickListener { requireActivity().finish() }
    }
}