package top.chilfish.compose.notepad.data


class NotepadRepository(private val dao: NotepadDao) {
    val allNotes = dao.queryAll()

    suspend fun insert(note: NoteEntity) = dao.insert(note)

    suspend fun delete(note: NoteEntity) = dao.delete(note)

    suspend fun deleteAll() = dao.deleteAll()
}