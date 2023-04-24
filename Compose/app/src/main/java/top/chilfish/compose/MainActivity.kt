package top.chilfish.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
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
import top.chilfish.compose.screen.LoginScreen
import top.chilfish.compose.screen.MessageScreen
import top.chilfish.compose.screen.ProfileScreen
import top.chilfish.compose.theme.ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeTheme() {


                Column {
                    HomeBar()

                    val (isMe, setIsMe) = rememberSaveable { mutableStateOf(true) }
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        Modifier.weight(1f)
                    ) {
                        composable("login") {
                            LoginScreen()
                        }

                        composable("home") {
                            ChatListScreen(
                                viewModel = ChatListViewModel(),
                                onChatSelected = { chatId ->
                                    navController.navigate("message/$chatId")
                                })
                        }

                        composable("contact") {
                            ContactScreen(viewModel = ContactViewModel(), onContactSelected = {})
                        }

                        composable("profile/{isMe}") {
                            ProfileScreen(profile = Profile(), isMe) {
                            }
                        }

                        composable(
                            "message/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getInt("id")
                            MessageScreen(viewModel = MessageViewModel(), Profile()) {
                                navController.popBackStack()
                            }
                        }
                    }
                    NavBar(navController)
                }
            }
        }
    }
}
