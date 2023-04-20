package top.chilfish.chatapp.entity

import android.content.Context
import com.google.gson.Gson
import top.chilfish.chatapp.Main
import java.io.Serializable

class Profile(
    var uid: String = "0",
    var name: String = "default",
    var avatar: String = "https://p.chilfish.top/avatar.webp",
    var email: String = "default@chilfish.top",
    var bio: String = "hello",
) : Serializable {

    // save profile to shared preference
    fun save2SP() {
        val sp = Main.AppCONTEXT!!.getSharedPreferences("profile", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("uid", uid)
        editor.putString("name", name)
        editor.putString("avatar", avatar)
        editor.putString("email", email)
        editor.putString("bio", bio)
        editor.apply()
    }

    companion object {
        @JvmStatic
        fun getProfile(json: String?): Profile {
            return Gson().fromJson(json, Profile::class.java)
        }

        @JvmStatic
        fun getJson(profile: Profile?): String {
            return Gson().toJson(profile)
        }

        @JvmStatic
        fun load(): Profile {
            val sp = Main.AppCONTEXT!!.getSharedPreferences("profile", Context.MODE_PRIVATE)
                ?: return Profile()
            val uid = sp.getString("uid", "0")!!
            val name = sp.getString("name", "default")!!
            val avatar = sp.getString("avatar", "https://p.chilfish.top/avatar.webp")!!
            val email = sp.getString("email", "default@chilfish.top")!!
            val bio = sp.getString("bio", "default bio")!!
            return Profile(uid, name, avatar, email, bio)
        }
    }
}