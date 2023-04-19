package top.chilfish.chatapp.helper

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

object TimeFormat {
    fun toString(time: Long, format: String?): String {
        val date = Date(time)
        @SuppressLint("SimpleDateFormat") val sdf = SimpleDateFormat(format)
        return sdf.format(date)
    }
}