package top.chilfish.compose.provider

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

const val ACCOUNT_SP = "Account"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = ACCOUNT_SP)

data class State(
    val isLogin: Boolean,
    val currUid: String
)

object AccountProvider {
    private lateinit var dataStore: DataStore<Preferences>
    private val KEY_IS_LOGIN = booleanPreferencesKey("isLogin")
    private val KEY_UID = stringPreferencesKey("uid")

    fun init(context: Context) {
        dataStore = context.dataStore
    }

    suspend fun saveLoginState(uid: String) {
        dataStore.edit {
            it[KEY_IS_LOGIN] = true
            it[KEY_UID] = uid
        }
    }

    suspend fun saveLogoutState() {
        dataStore.edit {
            it[KEY_IS_LOGIN] = false
            it[KEY_UID] = ""
        }
    }

    suspend fun getState(): State {
        return dataStore.data
            .catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }.map {
                State(
                    it[KEY_IS_LOGIN] ?: false,
                    it[KEY_UID] ?: ""
                )
            }.first()
    }
}
