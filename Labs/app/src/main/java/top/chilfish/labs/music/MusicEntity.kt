package top.chilfish.labs.music

import kotlinx.serialization.Serializable
import kotlin.time.Duration

data class MusicEntity(
    val id: String,
    val title: String,
    val artist: String,
    val duration: Duration,
    val cover: String,
)

@Serializable
data class MusicList(
    val name: String,
    val id: String,
    val artist: String,
    val cover: String,
)