package top.chilfish.compose.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import top.chilfish.compose.data.Profile

val currUid: MutableLiveData<String> = MutableLiveData("")

class ProfileViewModel : ViewModel() {
    private val _profile = MutableStateFlow(Profile())
    val profile: StateFlow<Profile> = _profile

    init {
        loadProfile()
    }

    private fun loadProfile() {
        _profile.value = Profile(
            name = "Chilfish",
            avatar = "https://p.chilfish.top/avatar.webp"
        )
    }

    fun onBtnClick(navController: NavController) {
        println("onBtnClick")
    }

    fun isMe(): Boolean {
        return currUid.value == profile.value.uid
    }
}