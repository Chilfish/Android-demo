package top.chilfish.compose.models

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import top.chilfish.compose.navigation.Routers

class LoginViewModel : ViewModel() {

    fun onUsnChange(usn: String) {
        println(usn)
        currUid.value = usn
    }

    fun onLogin(
        usn: String, psw: String,
        navController: NavController
    ) {
        currUid.value = usn
        navController.navigate(Routers.Home)
    }
}