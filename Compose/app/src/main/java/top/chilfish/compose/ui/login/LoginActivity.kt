package top.chilfish.compose.ui.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import top.chilfish.compose.BaseActivity
import top.chilfish.compose.ui.main.MainActivity

class LoginActivity : BaseActivity() {
    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPage(viewModel = loginViewModel)
        }
        initEvent()
    }

    private fun initEvent() {
        lifecycleScope.launch {
            loginViewModel.loginState.collect {
                when (it) {
                    LoginState.Success -> {
                        startActivity<MainActivity>()
                        finish()
                    }
                }
            }
        }
    }
}