package top.chilfish.chatapp.entity

import android.content.Context
import com.google.gson.Gson
import top.chilfish.chatapp.Main
import top.chilfish.chatapp.api.FetchData
import top.chilfish.chatapp.helper.JsonParser
import java.io.Serializable

class Profile : Serializable {
    var uid: String?
    var name: String?
    var avatar: String?
    var email: String?
    var bio: String? = null

    constructor() {
        uid = "0"
        name = "default"
        avatar = "https://p.chilfish.top/avatar.webp"
        email = "default@chilfish.top"
    }

    constructor(uid: String?, name: String?, avatar: String?, email: String?, bio: String?) {
        this.uid = uid
        this.name = name
        this.avatar = avatar
        this.email = email
        this.bio = bio
    }

    override fun toString(): String {
        return "Profile{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", bio='" + bio + '\'' +
                '}'
    }

    // save profile to shared preference
    fun save2SP() {
        val SP = Main.AppCONTEXT!!.getSharedPreferences("profile", Context.MODE_PRIVATE)
        val editor = SP.edit()
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
            val uid = sp.getString("uid", "0")
            val name = sp.getString("name", "default")
            val avatar = sp.getString("avatar", "https://p.chilfish.top/avatar.webp")
            val email = sp.getString("email", "default@chilfish.top")
            val bio = sp.getString("bio", "default bio")
            return Profile(uid, name, avatar, email, bio)
        }
    }
}