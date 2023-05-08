package top.chilfish.compose.room

import androidx.compose.runtime.saveable.listSaver
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val MAX_AGE = 150
const val TABLE_NAME = "user"

@Entity(tableName = TABLE_NAME)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    val name: String = "",
    @ColumnInfo(name = "age", typeAffinity = ColumnInfo.INTEGER)
    val age: Int = 0,
) {
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is UserEntity) return false
        return name == other.name && age == other.age
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}

fun newUser(old: UserEntity) =
    UserEntity(
        name = old.name,
        age = old.age
    )

val UserSaver = listSaver<UserEntity, Any>(
    save = {
        listOf(it.id, it.name, it.age)
    },
    restore = {
        UserEntity(
            id = it[0] as Long,
            name = it[1] as String,
            age = it[2] as Int,
        )
    }
)