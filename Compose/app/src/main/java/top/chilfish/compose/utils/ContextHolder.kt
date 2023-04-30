package top.chilfish.compose.utils

import android.app.Application

object ContextHolder {
    lateinit var context: Application
        private set

    fun init(application: Application) {
        this.context = application
    }
}