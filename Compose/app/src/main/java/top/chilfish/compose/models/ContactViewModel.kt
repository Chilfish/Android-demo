package top.chilfish.compose.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import top.chilfish.compose.Profile

class ContactViewModel : ViewModel() {
    private val _contacts = MutableStateFlow<List<Profile>>(emptyList())
    val contacts: StateFlow<List<Profile>> = _contacts

    init {
        loadContacts()
    }

    private fun loadContacts() {
        viewModelScope.launch {
            _contacts.value = listOf(
                Profile(
                    name = "Contact 1",
                    avatar = "https://p.chilfish.top/avatar.webp"
                ),
                Profile(
                    name = "Contact 2",
                    avatar = "https://p.chilfish.top/avatar1.webp"
                ),
                Profile(
                    name = "Contact 3",
                    avatar = "https://p.chilfish.top/avatar2.webp"
                ),
            )
        }
    }
}