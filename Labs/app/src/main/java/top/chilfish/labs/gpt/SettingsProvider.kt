package top.chilfish.labs.gpt

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val SETTINGS_SP = "Settings"
private val KEY_HOST = stringPreferencesKey("host")
private val KEY_KEY = stringPreferencesKey("key")
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SETTINGS_SP)

object SettingsProvider {
    private lateinit var dataStore: DataStore<Preferences>

    fun init(context: Context) {
        dataStore = context.dataStore
    }

    suspend fun saveSettings(key: String, host: String) {
        dataStore.edit {
            it[KEY_HOST] = host
            it[KEY_KEY] = key
        }
    }

    suspend fun getSettings(): Pair<String, String> {
        val pref = dataStore.data.first()
        val key = pref[KEY_KEY] ?: ""
        val host = pref[KEY_HOST] ?: "https://api.openai.com"
        return (key to host)
    }

}
