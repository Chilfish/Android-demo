package top.chilfish.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import top.chilfish.compose.components.HomeBar
import top.chilfish.compose.navigation.ChillNavHost
import top.chilfish.compose.navigation.NavBar
import top.chilfish.compose.navigation.Routers

@Composable
fun MainApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        modifier = modifier,
        topBar = { HomeBar() },
        bottomBar = { NavBar(navController) }
    ) { innerPadding ->
        ChillNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}