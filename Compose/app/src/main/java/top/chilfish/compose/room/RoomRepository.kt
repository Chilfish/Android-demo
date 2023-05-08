package top.chilfish.compose.room

class RoomRepository(private val UserDao: UserDao) {

    suspend fun addUser(user: UserEntity) = UserDao.insert(user)

    suspend fun queryByName(user: UserEntity) = UserDao.queryByName(user.name)

    suspend fun queryUser(user: UserEntity) = UserDao.queryById(user.id)

    suspend fun editUser(user: UserEntity) = UserDao.update(user)

    suspend fun deleteUser(user: UserEntity) = UserDao.delete(user)

    suspend fun deleteAll() = UserDao.deleteAll()

    val users = UserDao.queryAll()
}