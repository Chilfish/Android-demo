package top.chilfish.compose.provider

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

const val ACCOUNT_SP = "Account"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = ACCOUNT_SP)
var curUid: String = ""
var isLoggedIn = MutableStateFlow(false)

object AccountProvider {
    private lateinit var dataStore: DataStore<Preferences>
    private val KEY_IS_LOGIN = booleanPreferencesKey("isLogin")
    private val KEY_UID = stringPreferencesKey("uid")

    fun init(context: Context) {
        dataStore = context.dataStore

        CoroutineScope(Dispatchers.IO).launch {
            val pref = dataStore.data.first()

            isLoggedIn.value = pref[KEY_IS_LOGIN] ?: false
            curUid = pref[KEY_UID] ?: ""
        }
    }

    suspend fun setLogin(uid: String) {
        curUid = uid
        isLoggedIn.value = true
        dataStore.edit {
            it[KEY_IS_LOGIN] = true
            it[KEY_UID] = uid
        }
    }

    suspend fun setLogout() {
        curUid = ""
        isLoggedIn.value = false
        dataStore.edit {
            it[KEY_IS_LOGIN] = false
            it[KEY_UID] = ""
        }
    }
}
