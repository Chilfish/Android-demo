package top.chilfish.labs.gpt.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GPTRepository @Inject constructor(
    private val dao: GPTDao,
) {
    val allMessages = dao.getAll()

    suspend fun insert(message: MessageEntity) = dao.insert(message)
    suspend fun deleteAll() = dao.deleteAll()
}