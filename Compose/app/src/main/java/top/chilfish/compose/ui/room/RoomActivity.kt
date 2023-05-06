package top.chilfish.compose.ui.room

import android.os.Bundle
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import top.chilfish.compose.BaseActivity
import top.chilfish.compose.room.RoomRepository
import top.chilfish.compose.room.UserDatabase


class RoomActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database by lazy {
            UserDatabase.getDatabase(this)
        }
        val repository by lazy {
            RoomRepository(database.userDao())
        }
        val viewModel = RoomViewModel(repository)

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