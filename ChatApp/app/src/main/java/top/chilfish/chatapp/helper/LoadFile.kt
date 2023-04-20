package top.chilfish.chatapp.helper

import top.chilfish.chatapp.Main
import java.io.BufferedReader
import java.io.InputStreamReader

object LoadFile {
    // load text file from assets
    fun assetsString(fileName: String): String {
        val fileString = StringBuilder()
        try {
            Main.AppCONTEXT!!.assets.open(fileName).use { inputStream ->
                val reader = BufferedReader(InputStreamReader(inputStream))
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    fileString.append(line)
                }
                return fileString.toString()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}