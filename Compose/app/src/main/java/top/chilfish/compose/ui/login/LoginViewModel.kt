package top.chilfish.compose.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import top.chilfish.compose.data.ProfileProp
import top.chilfish.compose.data.fake.Accounts
import top.chilfish.compose.provider.AccountProvider
import top.chilfish.compose.provider.curUid
import top.chilfish.compose.utils.showToast

class LoginViewModel : ViewModel() {
    private val _loginState = MutableSharedFlow<LoginState>()
    val loginState: SharedFlow<LoginState> = _loginState

    fun goToLogin(usn: String, psw: String) {
        val user = Accounts.find(usn, ProfileProp.NAME) ?: return userNotFound()

        viewModelScope.launch {
            AccountProvider.setLogin(user.uid)
        }
        login()
    }

    private fun userNotFound() {
        showToast("User not found")
    }

    fun login() {
        if (curUid.isBlank()) return

        viewModelScope.launch {
            _loginState.emit(LoginState.Success)
        }
    }
}

enum class LoginState {
    Success
}