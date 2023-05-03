package top.chilfish.compose.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import top.chilfish.compose.components.CounterScreen
import top.chilfish.compose.components.HomeBar
import top.chilfish.compose.navigation.ChillNavHost
import top.chilfish.compose.navigation.NavBar
import top.chilfish.compose.navigation.Routers

@Composable
fun MainApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val curRouter = navBackStackEntry?.destination?.route ?: ""
    val showBars = curRouter != Routers.Message

//    Scaffold(
//        modifier = modifier,
//        topBar = {
//            if (showBars) HomeBar()
//        },
//        bottomBar = {
//            if (showBars) NavBar(navController)
//        },
//        content = { innerPadding ->
//            ChillNavHost(
//                navController = navController,
//                modifier = Modifier.padding(innerPadding),
//   )
//        }
//    )
    LazyColumn(
        modifier.padding(10.dp, 50.dp)
    ) {
        item {
            CounterScreen()
            CounterScreen()
        }
    }

}
