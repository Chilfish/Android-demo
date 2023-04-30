package top.chilfish.compose.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import top.chilfish.compose.components.MessageBar
import top.chilfish.compose.data.Profile
import top.chilfish.compose.data.fake.Accounts
import top.chilfish.compose.models.MessageViewModel

@Composable
fun MessageScreen(
    viewModel: MessageViewModel,
    navController: NavHostController
) {
    val messages = viewModel.messages.collectAsState()
    val profile = Accounts.find(viewModel.chatUid) ?: Profile()

    MessageBar(profile) {
        viewModel.onAvatarClick(navController)
    }
}
