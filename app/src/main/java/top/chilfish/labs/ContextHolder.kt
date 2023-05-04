package top.chilfish.labs

import android.app.Application

object ContextHolder {
    lateinit var context: Application
        private set

    fun init(application: Application) {
        this.context = application
    }
}