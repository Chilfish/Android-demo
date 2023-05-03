package top.chilfish.compose.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import top.chilfish.compose.BaseActivity
import top.chilfish.compose.provider.isLoggedIn
import top.chilfish.compose.ui.login.LoginActivity
import top.chilfish.compose.ui.login.LoginViewModel

class MainActivity : BaseActivity() {
    private val viewModel by viewModels<MainViewModel>()
    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel.login()

        setContent {
            MainApp()
        }

        lifecycleScope.launch {
            isLoggedIn.collect {
                if (!it) {
                    startActivity<LoginActivity>()
                    finish()
                }
            }
        }
    }
}
