package top.chilfish.labs.music

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.load
import coil.memory.MemoryCache
import kotlinx.coroutines.launch
import top.chilfish.labs.R
import top.chilfish.labs.base.BaseActivity
import top.chilfish.labs.databinding.ActivityMusicBinding


class MusicActivity : BaseActivity(), ImageLoaderFactory {

    private lateinit var binding: ActivityMusicBinding
    private val viewModel by viewModels<MusicViewModel>()


    private fun init() {
        binding = ActivityMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

        lifecycleScope.launch {
            viewModel.musicState.collect {
                binding.music = it.curSong
                binding.cover.load(it.curSong.cover)

                binding.pause.setImageResource(
                    if (it.isPlaying) {
                        R.drawable.baseline_pause_24
                    } else {
                        R.drawable.baseline_play_arrow_24
                    }
                )
            }
        }

        lifecycleScope.launch {
            viewModel.curPos.collect {
//                Log.d("Music", "pos: $it")
            }
        }

        binding.pause.setOnClickListener { viewModel.togglePlay() }
        binding.next.setOnClickListener { viewModel.nextSong() }
        binding.prev.setOnClickListener { viewModel.prevSong() }
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(this.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.release()
    }
}