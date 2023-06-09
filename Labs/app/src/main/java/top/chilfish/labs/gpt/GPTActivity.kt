package top.chilfish.labs.gpt

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import top.chilfish.labs.base.BaseActivity
import top.chilfish.labs.databinding.ActivityGptBinding

class GPTActivity : BaseActivity() {
    private lateinit var binding: ActivityGptBinding
    private val viewModel by viewModels<GPTViewModel>()

    private val adapter = MessageAdapter()
    private lateinit var rv: RecyclerView

    private fun init() {
        binding = ActivityGptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rv = binding.messList
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        binding.btnSend.setOnClickListener {
            val content = binding.chatInput.text.toString()
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
            adapter.updateItems(it.messages)
        }
    }
}