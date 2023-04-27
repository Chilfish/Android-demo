package top.chilfish.compose.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import top.chilfish.compose.data.Profile
import top.chilfish.compose.data.fake.Accounts
import top.chilfish.compose.navigation.NavigationActions
import top.chilfish.compose.navigation.Routers

val currUid: MutableLiveData<String> = MutableLiveData("")

class ProfileViewModel(private val uid: String) : ViewModel() {
    private val _profile = MutableStateFlow(Profile())
    val profile: StateFlow<Profile> = _profile

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _profile.value = Accounts.find(uid)
        }
    }

    fun onBtnClick(uid: String, navController: NavHostController) {
        if (!isMe()) {
            NavigationActions(navController).navigateTo(Routers.Message, uid)
        }
    }

    fun isMe(): Boolean {
        return currUid.value == profile.value.uid
    }
}