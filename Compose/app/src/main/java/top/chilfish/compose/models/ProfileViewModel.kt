package top.chilfish.compose.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import top.chilfish.compose.data.Profile
import top.chilfish.compose.data.fake.Accounts
import top.chilfish.compose.navigation.NavigationActions
import top.chilfish.compose.navigation.Routers
import top.chilfish.compose.provider.AccountProvider
import top.chilfish.compose.provider.curUid

class ProfileViewModel(private val uid: String) : ViewModel() {
    private val _profile = MutableStateFlow(Profile())
    val profile: StateFlow<Profile> = _profile

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _profile.value = Accounts.find(uid) ?: Profile()
        }
    }

    fun onBtnClick(uid: String, navController: NavHostController) {
        if (!isMe()) {
            NavigationActions(navController).navigateTo(Routers.Message, uid)
        } else {
            viewModelScope.launch {
                AccountProvider.setLogout()
            }
        }
    }

    fun isMe(): Boolean {
        return curUid == profile.value.uid
    }
}