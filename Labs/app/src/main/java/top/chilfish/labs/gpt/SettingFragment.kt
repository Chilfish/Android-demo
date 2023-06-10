package top.chilfish.labs.gpt

import android.os.Bundle
import android.util.Log
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import top.chilfish.labs.R
import top.chilfish.labs.base.BaseFragment
import top.chilfish.labs.databinding.FragGptSettingBinding
import top.chilfish.labs.utils.showToast

@AndroidEntryPoint
class SettingFragment(
    private val viewModel: GPTViewModel,
) : BaseFragment<FragGptSettingBinding>() {
    override val layoutId = R.layout.frag_gpt_setting

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val (_, key, baseHost) = viewModel.uiState.value
        binding.keyEdit.text?.append(key)
        binding.hostEdit.text?.append(baseHost)

        Log.d("GPT", "settings: ${(key to baseHost)}")
        binding.settingSave.setOnClickListener { events() }
    }

    private fun events() {
        val host = binding.hostEdit.text.toString()
        val key = binding.keyEdit.text.toString()

        Log.d("GPT", "input: ${(key to host)}")
        viewModel.saveSetting(key, host)
        showToast(getString(R.string.toast_save_success))
    }
}