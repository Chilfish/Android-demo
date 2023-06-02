package top.chilfish.labs.music

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import top.chilfish.labs.utils.LoadFile
import kotlin.random.Random


const val BaseURL = "http://music.163.com/song/media/outer/url?id="

class MusicViewModel : ViewModel() {
    private var _musicState = MutableStateFlow(MusicState())
    val musicState: StateFlow<MusicState> = _musicState

    private var _curPos = MutableStateFlow(0)
    val curPos: StateFlow<Int> = _curPos

    private var curSongDuration: Int = 0
    private var progressJob: Job? = null

    private val mediaPlayer: MediaPlayer = MediaPlayer().apply {
        setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setLegacyStreamType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )
    }

    init {
        _musicState.update {
            it.copy(allSongs = LoadFile.assetsJson("music.json"))
        }

        load(Random.nextInt(_musicState.value.allSongs.size))
    }

    private fun load(index: Int) = viewModelScope.launch {
        val music = _musicState.value.allSongs[index]

        if (music.id == _musicState.value.curSong.id) {
            mediaPlayer.seekTo(0)
            return@launch
        }

        val url = BaseURL + music.id

        Log.d("Music", "uri: $url")
        mediaPlayer
            .apply {
                reset()
                setDataSource(url)
                prepare()
                curSongDuration = mediaPlayer.duration
            }
        _curPos.value = 0

        _musicState.update {
            it.copy(
                curSong = Song(
                    id = music.id,
                    title = music.name,
                    artist = music.artist,
                    duration = curSongDuration,
                    cover = music.cover
                ),
                curIndex = index,
            )
        }
        Log.d("Music", "${_musicState.value.curSong}")
    }

    private fun startProgressUpdates() {
        progressJob?.cancel()
        progressJob = CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                val curPosition = mediaPlayer.currentPosition
                if (curPosition >= curSongDuration) {
                    stopProgressUpdates()
                }
                _curPos.value = curPosition
                delay(800)
            }
        }
    }

    private fun stopProgressUpdates() {
        progressJob?.cancel()
        progressJob = null
    }

    fun togglePlay() = if (mediaPlayer.isPlaying) pauseSong() else playSong()


    private fun playSong() {
        mediaPlayer.start()
        _musicState.update { it.copy(isPlaying = true) }
        startProgressUpdates()
    }

    private fun pauseSong() {
        mediaPlayer.pause()
        _musicState.update { it.copy(isPlaying = false) }
        stopProgressUpdates()
    }

    private fun move(step: Int) {
        var curIndex = 0
        _musicState.update {
            curIndex = (it.curIndex + step) % it.allSongs.size
            if (curIndex == -1) curIndex = it.allSongs.size - 1
            it.copy(curIndex = curIndex)
        }
        load(curIndex)
        playSong()
    }

    fun nextSong() = move(1)
    fun prevSong() = move(-1)

    fun release() = mediaPlayer.release()

    override fun onCleared() {
        super.onCleared()
        release()
    }
}

data class MusicState(
    val allSongs: MutableList<MusicListItem> = mutableListOf(),

    val curSong: Song = Song(),
    val curIndex: Int = 0,

    val isPlaying: Boolean = false,
)