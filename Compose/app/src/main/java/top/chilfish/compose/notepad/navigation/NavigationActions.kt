package top.chilfish.compose.notepad.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object Routers {
    const val noteId = "noteId"
    const val Home = "home"
    const val Detail = "detail/{$noteId}"
}

class NavigationActions(private val navController: NavHostController) {
    fun navigateTo(route: String, id: Long? = null) {
        val router = when (route) {
            Routers.Detail -> route.replace("{noteId}", id.toString())
            else -> route
        }

        navController.navigate(router) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
