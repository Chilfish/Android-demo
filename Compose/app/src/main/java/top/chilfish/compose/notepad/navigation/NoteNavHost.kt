package top.chilfish.compose.notepad.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import top.chilfish.compose.notepad.NoteDetail
import top.chilfish.compose.notepad.NotepadPage
import top.chilfish.compose.notepad.NotepadViewModel

@Composable
fun NoteNavHost(
    viewModel: NotepadViewModel
) {
    val navController = rememberNavController()
    viewModel.navController = navController

    NavHost(navController = navController, startDestination = Routers.Home) {
        composable(Routers.Home) {
            NotepadPage(viewModel = viewModel)
        }
        composable(
            Routers.Detail,
            arguments = listOf(navArgument(Routers.noteId) { type = NavType.LongType })
        ) {
            val uid = it.arguments?.getLong(Routers.noteId)
            NoteDetail(viewModel = viewModel, noteId = uid)
        }
    }
}