package top.chilfish.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import top.chilfish.compose.models.UIState
import top.chilfish.compose.provider.AccountProvider
import top.chilfish.compose.theme.ComposeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AccountProvider.init(this)

        setContent {
            ComposeTheme {
                MainApp(UIState)
            }
        }
    }
}
