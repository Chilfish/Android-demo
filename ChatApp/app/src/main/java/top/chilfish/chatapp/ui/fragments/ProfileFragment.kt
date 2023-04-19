package top.chilfish.chatapp.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.bumptech.glide.Glide
import top.chilfish.chatapp.R
import top.chilfish.chatapp.databinding.FragmentProfileBinding
import top.chilfish.chatapp.entity.Profile
import top.chilfish.chatapp.helper.LoginCheck.logout
import top.chilfish.chatapp.ui.activities.ChatMainActivity
import top.chilfish.chatapp.ui.activities.LoginActivity

class ProfileFragment : BaseFragment<FragmentProfileBinding?> {
    private var mProfile: Profile
    private var isContact = false
    private var logoutBtn: Button? = null
    private var sendMsgBtn: Button? = null

    constructor(profile: Profile) {
        mProfile = profile
    }

    constructor(profile: Profile, isContact: Boolean) {
        mProfile = profile
        this.isContact = isContact
    }

    override val layoutId: Int
        get() = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData(view)
        if (!isContact) {
            logoutEvent(view)
        } else {
            sendMessageEvent(view)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindData(view: View) {
        logoutBtn = binding!!.profileLogout
        sendMsgBtn = binding!!.profileSendMessage
        try {
            Glide.with(view.context)
                .load(mProfile.avatar)
                .into(binding!!.profileAvatar)
            binding!!.profileName.text = mProfile.name
            binding!!.profileUid.text = "uid: " + mProfile.uid
            binding!!.profileEmail.text = mProfile.email
            binding!!.profileBio.text = mProfile.bio
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun logoutEvent(view: View) {
        logoutBtn!!.visibility = View.VISIBLE
        sendMsgBtn!!.visibility = View.GONE
        logoutBtn!!.setOnClickListener {
            logout(view.context)
            val intent = Intent(view.context, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun sendMessageEvent(view: View) {
        sendMsgBtn!!.visibility = View.VISIBLE
        logoutBtn!!.visibility = View.GONE
        sendMsgBtn!!.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("profile", mProfile)
            val intent = Intent(view.context, ChatMainActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }
}