package top.chilfish.labs.music

import android.os.Bundle
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import top.chilfish.labs.R
import top.chilfish.labs.base.BaseActivity
import top.chilfish.labs.databinding.ActivityMusicBinding
import top.chilfish.labs.utils.formattedTime

class MusicActivity : BaseActivity() {

    private lateinit var binding: ActivityMusicBinding
    private val viewModel by viewModels<MusicViewModel>()

    private fun init() {
        binding = ActivityMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        watchData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

        binding.seekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean,
            ) {
                if (fromUser) {
                    viewModel.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                viewModel.pauseSong()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                viewModel.playSong()
            }
        })

        binding.pause.setOnClickListener { viewModel.togglePlay() }
        binding.next.setOnClickListener { viewModel.nextSong() }
        binding.prev.setOnClickListener { viewModel.prevSong() }
    }

    private fun watchData() = lifecycleScope.launch {
        launch {
            viewModel.musicState
                .distinctUntilChanged { old, new ->
                    old.curSong.id == new.curSong.id
                }
                .collect {
                    val curSong = it.curSong

                    binding.music = curSong
                    binding.cover.load(curSong.cover)
                    binding.seekBar.max = curSong.duration
                    binding.totalTime.text = formattedTime(curSong.duration)
                }
        }

        launch {
            viewModel.musicState.collect {
                binding.pause.setImageResource(
                    if (it.isPlaying) {
                        R.drawable.baseline_pause_24
                    } else {
                        R.drawable.baseline_play_arrow_24
                    }
                )
            }
        }

        launch {
            viewModel.curPos.collect {
                binding.seekBar.progress = it
                binding.elapsedTime.text = formattedTime(it)
            }
        }
    }
}