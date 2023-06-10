package top.chilfish.labs

import android.app.Application
import com.drake.brv.utils.BRV
import com.drake.net.NetConfig
import com.drake.net.okhttp.setConverter
import com.drake.net.okhttp.setDebug
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import okhttp3.Cache
import top.chilfish.labs.gpt.SettingsProvider
import top.chilfish.labs.gpt.data.GPTDatabase
import top.chilfish.labs.gpt.data.GPTRepository
import top.chilfish.labs.module.ApplicationScope
import top.chilfish.labs.module.IODispatcher
import top.chilfish.labs.notepad.data.NoteDatabase
import top.chilfish.labs.notepad.data.NoteRepository
import top.chilfish.labs.utils.SerializationConverter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

val JSON = Json {
    encodeDefaults = true
    ignoreUnknownKeys = true
    coerceInputValues = true
}

@HiltAndroidApp
class MainApplication : Application() {
    private val noteDatabase by lazy { NoteDatabase.getDatabase(this, applicationScope) }
    val noteRepository by lazy { NoteRepository(noteDatabase.noteDao()) }

    @Inject
    @ApplicationScope
    lateinit var applicationScope: CoroutineScope

    @Inject
    @IODispatcher
    lateinit var ioDispatcher: CoroutineDispatcher

    @Inject
    lateinit var gptDB: GPTDatabase

    @Inject
    lateinit var gptRepo: GPTRepository

    override fun onCreate() {
        super.onCreate()
        BRV.modelId = BR.m

        SettingsProvider.init(this)
        NetConfig.initialize(context = applicationContext) {
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)

            setDebug(true)
            setConverter(SerializationConverter())
            cache(Cache(cacheDir, 1024 * 1024 * 128))
        }
    }
}