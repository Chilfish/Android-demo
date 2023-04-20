package top.chilfish.chatapp.helper

import android.content.Context
import android.util.Base64
import android.util.Log
import top.chilfish.chatapp.Main
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Objects

object LoginCheck {
    private const val CSV_File_Name = "users.csv"
    private const val Status_Key = "status"
    private const val Pref_Key = "UserData"
    private const val CSV_SEPARATOR = ","
    private const val SALT = "ChILL_SALT"
    private const val TAG = "LoginCheck"

    @JvmStatic
    fun isLoggedIn(context: Context): Boolean {
        return context.getSharedPreferences(Pref_Key, Context.MODE_PRIVATE)
            .getBoolean(Status_Key, false)
    }

    @JvmStatic
    fun logout(context: Context) {
        val pref = context.getSharedPreferences(Pref_Key, Context.MODE_PRIVATE)
        pref.edit().putBoolean(Status_Key, false).apply()
    }

    @JvmStatic
    fun saveUser(context: Context, username: String, password: String) {
        val csvRow = username + CSV_SEPARATOR +
                encryptPassword(password) + CSV_SEPARATOR + true
        context.getSharedPreferences(Pref_Key, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(Status_Key, true)
            .apply()
        try {
            val writer = FileWriter(csvFile, true)
            writer.write(csvRow)
            writer.append('\n')
            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun encryptPassword(password: String): String {
        try {
            val md = MessageDigest.getInstance("SHA-256")
            md.update((password + SALT).toByteArray())
            val digest = md.digest()
            return Base64.encodeToString(digest, Base64.NO_WRAP)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }

    @get:Throws(IOException::class)
    private val csvFile: File
        get() {
            val context = Main.AppCONTEXT!!
            val file = File(context.filesDir, CSV_File_Name)
            if (!file.exists()) {
                val status = file.createNewFile()
                if (!status) {
                    Log.e(TAG, "getCsvFile: 创建文件失败")
                }
            }
            return file
        }

    private val csvLines: List<String>
        get() {
            val lines = ArrayList<String>()
            try {
                val file = csvFile
                val reader = BufferedReader(FileReader(file))
                var line: String

                while (reader.readLine().also { line = it } != null) {
                    lines.add(line)
                }
                reader.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return lines
        }

    @JvmStatic
    fun isUserExists(username: String): Boolean {
        if (csvLines.isEmpty()) {
            return false
        }
        for (line in csvLines) {
            val user = line.split(CSV_SEPARATOR.toRegex())
                .dropLastWhile { it.isEmpty() }
                .toTypedArray()[0]

            if (user == username) {
                return true
            }
        }
        return false
    }

    @JvmStatic
    fun isPasswordVide(context: Context, username: String, password: String): Boolean {
        if (csvLines.isEmpty()) {
            return false
        }
        val csvRow = username + CSV_SEPARATOR + encryptPassword(password)
        for (line in csvLines) {
            val l = line.split(CSV_SEPARATOR.toRegex())
                .dropLastWhile { it.isEmpty() }
                .toTypedArray()

            val user = l[0] + CSV_SEPARATOR + l[1]
            if (user == csvRow) {
                context.getSharedPreferences(Pref_Key, Context.MODE_PRIVATE)
                    .edit()
                    .putBoolean(Status_Key, true)
                    .apply()
                return true
            }
        }
        return false
    }
}