package top.chilfish.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import top.chilfish.compose.components.AppBars
import top.chilfish.compose.components.HomeBar
import top.chilfish.compose.components.TextFieldWithErrorState
import top.chilfish.compose.navigation.ChillNavHost
import top.chilfish.compose.navigation.NavBar

@Preview(showBackground = true)
@Composable
fun MainApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
//    AppBars()

    Scaffold(
        modifier = modifier,
        topBar = { HomeBar() },
        bottomBar = { NavBar(navController) },
        content = { innerPadding ->
            ChillNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
//            TextFieldWithErrorState(
//                modifier = Modifier.padding(innerPadding)
//            )
        }
    )
}