package top.chilfish.compose.navigation

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import top.chilfish.compose.R

object Routers {
    const val Login = "login"
    const val Home = "home"
    const val Contact = "contact"
    const val Message = "message/{chatId}" // TODO 传递参数
    const val Profile = "profile/{uid}"
}

data class NavBarDes(
    val router: String,
    @StringRes val iconTextId: Int,
    @DrawableRes val selectedIcon: Int
)

class NavigationActions(private val navController: NavHostController) {
    fun navigateTo(route: String, uid: String) {
        val router = when (route) {
            Routers.Message -> route.replace("{chatId}", uid)
            Routers.Profile -> route.replace("{uid}", uid)
            else -> route
        }

        Log.d("Nav", "navigateTo: $uid")

        navController.navigate(router) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
//            popUpTo(navController.graph.findStartDestination().id) {
//                saveState = true
//            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
}

val NavBars = listOf(
    NavBarDes(
        router = Routers.Home,
        iconTextId = R.string.home,
        selectedIcon = R.drawable.outline_chat_24
    ),
    NavBarDes(
        router = Routers.Contact,
        iconTextId = R.string.contacts,
        selectedIcon = R.drawable.contact
    ),
    NavBarDes(
        router = Routers.Profile,
        iconTextId = R.string.profile,
        selectedIcon = R.drawable.outline_person_24
    )
)