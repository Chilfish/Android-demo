package top.chilfish.chatapp

import android.app.Application
import android.content.Context
import android.content.Intent
import top.chilfish.chatapp.helper.LoginCheck
import top.chilfish.chatapp.ui.activities.LoginActivity
import top.chilfish.chatapp.ui.activities.MainActivity

class Main : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCONTEXT = applicationContext
        val intent: Intent = if (LoginCheck.isLoggedIn(this)) {
            Intent(AppCONTEXT, MainActivity::class.java)
        } else {
            Intent(AppCONTEXT, LoginActivity::class.java)
        }
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    companion object {
        @JvmStatic
        var AppCONTEXT: Context? = null
    }
}