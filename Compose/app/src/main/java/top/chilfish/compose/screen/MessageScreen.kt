package top.chilfish.compose.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import top.chilfish.compose.Profile
import top.chilfish.compose.components.MessageBar
import top.chilfish.compose.models.MessageViewModel

@Composable
fun MessageScreen(
    profile: Profile,
    viewModel: MessageViewModel,
    onBack: () -> Unit
) {
    val message = viewModel.message.collectAsState()
    MessageBar(profile = profile)
}
