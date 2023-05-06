package top.chilfish.compose.room

import androidx.compose.runtime.saveable.listSaver
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    val name: String = "",
    @ColumnInfo(name = "age", typeAffinity = ColumnInfo.INTEGER)
    val age: Int = 0,
)

const val MAX_AGE = 150

val UserSaver = listSaver<UserEntity, Any>(
    save = {
        listOf(it.id, it.name, it.age)
    },
    restore = {
        UserEntity(
            id = it[0] as Int,
            name = it[1] as String,
            age = it[2] as Int,
        )
    }
)