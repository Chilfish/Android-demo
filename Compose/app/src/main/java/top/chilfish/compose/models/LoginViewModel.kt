package top.chilfish.compose.models

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import top.chilfish.compose.navigation.Routers
import top.chilfish.compose.provider.AccountProvider

class LoginViewModel : ViewModel() {
    suspend fun onLogin(
        uiState: UIState,
        usn: String, psw: String,
        navController: NavController
    ) {
        uiState.setCurrUid(usn)
        AccountProvider.saveLoginState(usn)
        navController.navigate(Routers.Home)
    }
}