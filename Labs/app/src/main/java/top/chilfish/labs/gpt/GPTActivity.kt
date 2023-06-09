package top.chilfish.labs.gpt

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.utils.linear
import com.drake.brv.utils.setDifferModels
import com.drake.brv.utils.setup
import kotlinx.coroutines.launch
import top.chilfish.labs.R
import top.chilfish.labs.base.BaseActivity
import top.chilfish.labs.databinding.ActivityGptBinding
import top.chilfish.labs.gpt.data.MessageEntity

class GPTActivity : BaseActivity() {
    private lateinit var binding: ActivityGptBinding
    private val viewModel by viewModels<GPTViewModel>()

    private lateinit var rv: RecyclerView

    private fun init() {
        binding = ActivityGptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rv = binding.messList
        rv.linear().setup {
            addType<MessageEntity>(R.layout.item_message)
        }.models = mutableListOf()

        binding.btnSend.setOnClickListener {
            val content = binding.chatInput.text.toString()
            binding.chatInput.text.clear()
            viewModel.send(content)
        }

        watch()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun watch() = lifecycleScope.launch {
        viewModel.uiState.collect {
            Log.d("GPT", "all: ${it.messages}")
            rv.setDifferModels(it.messages)
        }
    }
}