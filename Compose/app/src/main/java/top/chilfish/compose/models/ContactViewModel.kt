package top.chilfish.compose.models

import android.util.Log
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

class ContactViewModel : ViewModel() {
    private val _contacts = MutableStateFlow<List<Profile>>(emptyList())
    val contacts: StateFlow<List<Profile>> = _contacts

    init {
        loadContacts()
    }

    private fun loadContacts() {
        viewModelScope.launch {
            _contacts.value = Accounts.contacts
        }
    }

    fun onContactSelected(contactId: String, navController: NavHostController) {
        Log.d("Nav", "uid: $contactId")

        NavigationActions(navController).navigateTo(Routers.Profile, contactId)
    }
}