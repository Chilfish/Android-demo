package top.chilfish.chatapp.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import top.chilfish.chatapp.R
import top.chilfish.chatapp.api.FetchData
import top.chilfish.chatapp.database.ChatListDB
import top.chilfish.chatapp.database.ContactDB
import top.chilfish.chatapp.entity.ChatItem
import top.chilfish.chatapp.entity.Profile
import top.chilfish.chatapp.helper.LoginCheck
import top.chilfish.chatapp.ui.fragments.ChatListFragment
import top.chilfish.chatapp.ui.fragments.ContactFragment
import top.chilfish.chatapp.ui.fragments.ProfileFragment

class MainActivity : AppCompatActivity() {
    private var chatListFragment: ChatListFragment? = null
    private var profileFragment: ProfileFragment? = null
    private var contactFragment: ContactFragment? = null
    private var chatItems: List<ChatItem>? = null
    private var contacts: List<Profile>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkLogin()
        FetchData.all()

        val pref = getSharedPreferences("status", MODE_PRIVATE)
        if (pref.getBoolean("isFirst", true)) {
            FetchData.saveToDB(this)
            pref.edit().putBoolean("isFirst", false).apply()
        }
        loadDB()
        chatListFragment = ChatListFragment(chatItems!!)
        profileFragment = ProfileFragment(Profile.load())
        contactFragment = contacts?.let { ContactFragment(it) }
        navChange()
    }

    @SuppressLint("NonConstantResourceId")
    private fun navChange() {
        val btnNav = findViewById<BottomNavigationView>(R.id.nav_btn)
        val id = R.id.frag_main

        btnNav.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.nav_chat -> {
                    replaceFragment(chatListFragment, id)
                }

                R.id.nav_profile -> {
                    replaceFragment(profileFragment, id)
                }

                R.id.nav_contact -> {
                    replaceFragment(contactFragment, id)
                }
            }
            true
        }
        replaceFragment(chatListFragment, id)
    }

    private fun replaceFragment(fragment: Fragment?, id: Int) {
        supportFragmentManager.beginTransaction()
            .replace(id, fragment!!)
            .commit()
    }

    private fun checkLogin() {
        if (!LoginCheck.isLoggedIn(this)) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addCategory(Intent.CATEGORY_HOME)
        startActivity(intent)
        finish()
    }

    private fun loadDB() {
        try {
            val chatListDB = ChatListDB(this)
            chatItems = chatListDB.all

            val contactDB = ContactDB(this)
            contacts = contactDB.all
            chatListDB.close()
            contactDB.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}