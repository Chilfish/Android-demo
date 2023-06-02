package top.chilfish.labs.music

import android.os.Bundle
import android.util.Log
import top.chilfish.labs.base.BaseActivity
import top.chilfish.labs.databinding.ActivityMusicBinding
import top.chilfish.labs.utils.LoadFile

const val BaseURL = "http://music.163.com/song/media/outer/url?id="

class MusicActivity : BaseActivity() {

    private lateinit var binding: ActivityMusicBinding

    private lateinit var musicList: MutableList<MusicList>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMusicBinding.inflate(layoutInflater)
        musicList = LoadFile.assetsJson("music.json")

        Log.d("Music", "music list: $musicList")
    }

}