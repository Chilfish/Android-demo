package top.chilfish.compose.ui.room

import android.os.Bundle
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import top.chilfish.compose.BaseActivity
import top.chilfish.compose.ChatApplication
import top.chilfish.compose.room.RoomRepository
import top.chilfish.compose.room.UserDatabase


class RoomActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(
            this,
            RoomViewModelFactory((application as ChatApplication).userRepository)
        )[RoomViewModel::class.java]

        setContent {
            Scaffold { padding ->
                RoomPage(
                    modifier = Modifier.padding(padding),
                    viewModel = viewModel
                )
            }
        }
    }
}