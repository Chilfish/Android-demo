package top.chilfish.chatapp.helper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TimeFormat {
    fun formatDate(time: Long, format: String?): String {
        val date = Date(time)
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(date)
    }

    fun toTimestamp(dateString: String, format: String): Long {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        val date = sdf.parse(dateString)
        return date?.time ?: 0
    }
}