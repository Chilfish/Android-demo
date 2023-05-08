package top.chilfish.compose

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import top.chilfish.compose.provider.AccountProvider
import top.chilfish.compose.room.RoomRepository
import top.chilfish.compose.room.UserDatabase
import top.chilfish.compose.utils.ContextHolder

class ChatApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    private val userDatabase by lazy {
        UserDatabase.getDatabase(this, applicationScope)
    }
    val userRepository by lazy {
        RoomRepository(userDatabase.userDao())
    }

    override fun onCreate() {
        super.onCreate()
        ContextHolder.init(this)
        AccountProvider.init(this)
    }
}