package top.chilfish.compose.utils

import android.os.Handler
import android.os.Looper
import android.widget.Toast

private val mainHandler by lazy {
    Handler(Looper.getMainLooper())
}

fun showToast(msg: Any?) {
    val message = msg?.toString()
    if (message.isNullOrBlank()) {
        return
    }
    if (Looper.myLooper() == Looper.getMainLooper()) {
        showToast(message = message)
    } else {
        mainHandler.post {
            showToast(message = message)
        }
    }
}

private fun showToast(message: String) {
    Toast.makeText(
        ContextHolder.context, message, Toast.LENGTH_SHORT
    ).show()
}