package top.chilfish.compose.ui.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import top.chilfish.compose.room.RoomRepository
import top.chilfish.compose.room.UserEntity
import top.chilfish.compose.utils.showToast

class RoomViewModel(
    private val repository: RoomRepository
) : ViewModel() {
    private var _pageState = MutableStateFlow(RoomPageState())
    val pageState: StateFlow<RoomPageState> = _pageState

    init {
        loadUsers()
    }

    fun loadUsers() = viewModelScope.launch {
        repository.users
            .collect {
                _pageState.value = RoomPageState(users = it)
            }
    }

    fun addUser(user: UserEntity) = viewModelScope.launch {
        if (repository.queryByName(user) != null) {
            showToast("user already exist")
            return@launch
        }

        val res = repository.addUser(user)
        if (res > 0) {
            showToast("addUser success")
        } else {
            showToast("addUser failed")
        }
    }

    fun queryUser(user: UserEntity) = viewModelScope.launch {
        val res = repository.queryByName(user) ?: run {
            showToast("user not exist")
            return@launch
        }

        _pageState.value = RoomPageState(users = listOf(res))
    }

    fun deleteUser(user: UserEntity) = viewModelScope.launch {
        _pageState.value = _pageState.value.copy(selectedUser = user, isShowAlert = true)
    }

    fun confirmDeleteUser() = viewModelScope.launch {
        val user = _pageState.value.selectedUser ?: return@launch
        val res = repository.deleteUser(user)
        if (res > 0) {
            showToast("deleteUser success")
        } else {
            showToast("deleteUser failed")
        }
    }

    fun editUser(user: UserEntity) = viewModelScope.launch {
        val res = repository.editUser(user)
        if (res > 0) {
            showToast("editUser success")
        } else {
            showToast("editUser failed")
        }
    }
}

class RoomViewModelFactory(
    private val repository: RoomRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoomViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RoomViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

data class RoomPageState(
    val users: List<UserEntity> = emptyList(),
    val selectedUser: UserEntity? = null,
    val isShowAlert: Boolean = false,
)
