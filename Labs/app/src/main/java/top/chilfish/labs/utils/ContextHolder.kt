package top.chilfish.labs.utils

import android.app.Application

object ContextHolder {
    lateinit var context: Application
        private set

    fun init(application: Application) {
        context = application
    }
}