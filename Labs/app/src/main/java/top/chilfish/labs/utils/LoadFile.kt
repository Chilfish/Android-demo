package top.chilfish.labs.utils

import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader

object LoadFile {
    // load text file from assets
    fun assetsString(fileName: String): String {
        val fileString = StringBuilder()
        try {
            ContextHolder.context.assets.open(fileName).use { inputStream ->
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

    inline fun <reified T> assetsJson(fileName: String): T {
        return Json.decodeFromString(assetsString(fileName))
    }
}