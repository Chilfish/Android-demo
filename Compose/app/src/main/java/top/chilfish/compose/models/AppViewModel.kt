package top.chilfish.compose.models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import top.chilfish.compose.navigation.Routers
import top.chilfish.compose.provider.AccountProvider

object UIState : ViewModel() {
    private val _isLogin = MutableStateFlow(false)
    val isLogin: StateFlow<Boolean> = _isLogin

    private val _currUid = MutableStateFlow("")
    val currUid: StateFlow<String> = _currUid

    init {
        viewModelScope.launch {
            val data = AccountProvider.getState()
            withContext(Dispatchers.IO) {
                _isLogin.value = data.isLogin
                _currUid.value = data.currUid
            }
        }
    }

    fun setLoginState(isLogin: Boolean) {
        _isLogin.value = isLogin
    }

    fun setCurrUid(currUid: String) {
        _currUid.value = currUid
    }

    fun loadStartRoute(): String {
        return if (isLogin.value) {
            Routers.Home
        } else {
            Routers.Login
        }
    }
}
