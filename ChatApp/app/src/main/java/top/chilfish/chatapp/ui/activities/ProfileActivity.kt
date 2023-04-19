package top.chilfish.chatapp.ui.activities

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import top.chilfish.chatapp.R
import top.chilfish.chatapp.entity.Profile
import top.chilfish.chatapp.ui.fragments.ProfileFragment

class ProfileActivity : AppCompatActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_profile)
        val profileBundle = intent.extras ?: return

        val profile = profileBundle.getSerializable("profile") as Profile

        val profileFragment = ProfileFragment(profile, true)
        profileFragment.arguments = profileBundle
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frag_profile, profileFragment)
            .commit()
        val back = findViewById<ImageButton>(R.id.btn_profile_back)
        back.setOnClickListener { finish() }
    }
}