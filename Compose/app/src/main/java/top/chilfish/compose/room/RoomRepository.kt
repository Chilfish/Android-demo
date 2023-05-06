package top.chilfish.compose.room

class RoomRepository(private val UserDao: UserDao) {

    suspend fun addUser(user: UserEntity) = UserDao.insertUser(user)

    suspend fun queryUser(user: UserEntity) = UserDao.queryUserByName(user.name)

    suspend fun editUser(user: UserEntity) = UserDao.updateUser(user)

    suspend fun deleteUser(user: UserEntity) = UserDao.deleteUser(user)

    val users = UserDao.queryAllUser()
}