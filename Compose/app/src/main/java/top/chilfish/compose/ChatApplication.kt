package top.chilfish.compose

import android.app.Application
import top.chilfish.compose.provider.AccountProvider
import top.chilfish.compose.utils.ContextHolder

class ChatApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ContextHolder.init(this)
        AccountProvider.init(this)
    }
}