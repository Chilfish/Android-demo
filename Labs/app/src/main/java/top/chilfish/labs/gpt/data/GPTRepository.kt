package top.chilfish.labs.gpt.data

import android.util.Log
import com.drake.net.Post
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import org.json.JSONObject
import top.chilfish.labs.JSON
import top.chilfish.labs.base.BaseRequest
import top.chilfish.labs.module.IODispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GPTRepository @Inject constructor(
    private val dao: GPTDao,

    private val api: BaseRequest,
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher,
) {
    val allMessages = dao.getAll()

    suspend fun insert(message: MessageEntity) = dao.insert(message)
    suspend fun deleteAll() = dao.deleteAll()

    suspend fun send(
        key: String,
        baseHost: String,
        messages: List<ChatMessage>,
    ): ChatMessage? = withContext(ioDispatcher) {
        val body = JSON.encodeToString(RequestBody(messages = messages))
        Log.d("GPT", "request: $body\n $key")

        val res = try {
            api.request {
                Post<String>("$baseHost/v1/chat/completions") {
                    addHeader("Authorization", "Bearer $key")
                    json(body)
                }
            }?.trimIndent()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } ?: return@withContext null

        val resJson = JSONObject(res)
        try {
            val messageObject = resJson
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")

            val resMessage = ChatMessage(
                role = Role.assistant,
                content = messageObject.getString("content")
            )
            Log.d("GPT", "res: $resMessage")

            resMessage
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}