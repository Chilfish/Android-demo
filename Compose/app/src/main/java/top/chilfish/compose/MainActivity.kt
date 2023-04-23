package top.chilfish.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import top.chilfish.compose.components.HomeBar
import top.chilfish.compose.components.NavBar
import top.chilfish.compose.models.ChatListViewModel
import top.chilfish.compose.models.ContactViewModel
import top.chilfish.compose.models.MessageViewModel
import top.chilfish.compose.screen.ChatListScreen
import top.chilfish.compose.screen.ContactScreen
import top.chilfish.compose.screen.MessageScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            Column {
                HomeBar()

                ChatListScreen(viewModel = ChatListViewModel(), onChatSelected = {})

                NavBar()
            }
        }
    }
}