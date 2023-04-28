package top.chilfish.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import top.chilfish.compose.components.HomeBar
import top.chilfish.compose.models.UIState
import top.chilfish.compose.navigation.ChillNavHost
import top.chilfish.compose.navigation.NavBar
import top.chilfish.compose.navigation.Routers

@Composable
fun MainApp(
    uiState: UIState,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val curRouter = navBackStackEntry?.destination?.route ?: ""
    val showBars = curRouter != Routers.Message && curRouter != Routers.Login

    Scaffold(
        modifier = modifier,
        topBar = {
            if (showBars) HomeBar()
        },
        bottomBar = {
            if (showBars) NavBar(uiState, navController)
        },
        content = { innerPadding ->
            ChillNavHost(
                uiState = uiState,
                navController = navController,
                modifier = Modifier.padding(innerPadding),
            )
        }
    )
}
