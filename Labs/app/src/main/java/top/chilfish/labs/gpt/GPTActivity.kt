package top.chilfish.labs.gpt

import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
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
import top.chilfish.labs.databinding.ItemMessageBinding
import top.chilfish.labs.gpt.data.MessageEntity
import top.chilfish.labs.gpt.data.Role
import top.chilfish.labs.utils.alert

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
            models = mutableListOf()
            onBind {
                val bind = getBinding<ItemMessageBinding>()
                val item = getModel<MessageEntity>()

                val layoutParams = bind.messBody.layoutParams as LinearLayout.LayoutParams
                if (item.role == Role.user) {
                    layoutParams.gravity = Gravity.END
                    bind.messBody.setBackgroundResource(R.drawable.message_r)
                } else {
                    layoutParams.gravity = Gravity.START
                    bind.messBody.setBackgroundResource(R.drawable.message_l)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        events()
        watch()
    }

    private fun watch() = lifecycleScope.launch {
        viewModel.uiState.collect {
            rv.setDifferModels(it.messages)
        }
    }

    private fun events() {
        binding.btnSend.setOnClickListener {
            val content = binding.chatInput.text.toString()
            binding.chatInput.text.clear()
            viewModel.send(content)
        }

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.setting -> {
                    replaceFragment(SettingFragment(viewModel), R.id.frag_gpt)
                    true
                }

                else -> false
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            alert(
                context = this,
                title = getString(R.string.alert_title_delete),
                subtitle = getString(R.string.alert_subtitle_delete),
                cancel = getString(R.string.cancel),
                confirm = getString(R.string.confirm),
                cancelAction = {},
                confirmAction = {
                    viewModel.deleteAll()
                }
            ).show()
        }
    }
}