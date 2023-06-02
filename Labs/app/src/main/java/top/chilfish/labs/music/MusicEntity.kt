package top.chilfish.labs.music

import kotlinx.serialization.Serializable
import kotlin.time.Duration

data class Song(
    val id: String = "",
    val title: String = "",
    val artist: String = "",
    val duration: Int = 0,
    val cover: String = "",
)

@Serializable
data class MusicListItem(
    val name: String,
    val id: String,
    val artist: String,
    val cover: String,
)