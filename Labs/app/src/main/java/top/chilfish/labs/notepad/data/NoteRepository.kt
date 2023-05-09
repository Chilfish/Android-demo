package top.chilfish.labs.notepad.data

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes = noteDao.getAll()

    suspend fun insert(note: NoteEntity) = noteDao.insert(note)

    suspend fun delete(note: NoteEntity) = noteDao.delete(note)

    suspend fun update(note: NoteEntity) = noteDao.update(note)
}