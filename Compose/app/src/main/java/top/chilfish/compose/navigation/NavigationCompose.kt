package top.chilfish.compose.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import top.chilfish.compose.models.ChatListViewModel
import top.chilfish.compose.models.ContactViewModel
import top.chilfish.compose.models.MessageViewModel
import top.chilfish.compose.models.ProfileViewModel
import top.chilfish.compose.provider.curUid
import top.chilfish.compose.screen.ChatListScreen
import top.chilfish.compose.screen.ContactScreen
import top.chilfish.compose.screen.MessageScreen
import top.chilfish.compose.screen.ProfileScreen

@Composable
fun NavBar(
    navController: NavHostController,
) {
    val (selectedItem, setSelectedItem) = rememberSaveable { mutableStateOf(0) }

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(74.dp)
    ) {
        NavBars.forEachIndexed { index, navBar ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = {
                    setSelectedItem(index)
                    NavigationActions(navController).navigateTo(navBar.router, curUid)
                },
                icon = {
                    Icon(
                        painter = painterResource(navBar.selectedIcon),
                        contentDescription = stringResource(navBar.iconTextId),
                        modifier = Modifier.height(24.dp)
                    )
                },
                label = {
                    Text(
                        text = stringResource(navBar.iconTextId),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            )
        }
    }
}

@Composable
fun ChillNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        NavHost(
            navController = navController,
            startDestination = Routers.Home,
        ) {

            composable(Routers.Home) {
                ChatListScreen(
                    viewModel = ChatListViewModel(),
                    navController = navController
                )
            }

            composable(Routers.Contact) {
                ContactScreen(
                    viewModel = ContactViewModel(),
                    navController = navController
                )
            }

            composable(
                Routers.Profile,
                arguments = listOf(navArgument("uid") { type = NavType.StringType })
            ) {
                val uid = it.arguments?.getString("uid")!!
                ProfileScreen(
                    viewModel = ProfileViewModel(uid),
                    navController = navController
                )
            }

            composable(
                Routers.Message,
                arguments = listOf(navArgument("chatId") { type = NavType.StringType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("chatId")!!
                MessageScreen(
                    viewModel = MessageViewModel(id),
                    navController = navController
                )
            }
        }
    }
}