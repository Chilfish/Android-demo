package top.chilfish.labs.sqlite

import android.annotation.SuppressLint
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.google.android.material.textfield.TextInputEditText
import top.chilfish.labs.databinding.ActivitySqliteBinding

@SuppressLint("StaticFieldLeak")
object ViewProvider {
    lateinit var nameInput: TextInputEditText
    lateinit var phoneInput: TextInputEditText
    private lateinit var operatorSheet: LinearLayout
    private lateinit var confirmSheet: LinearLayout
    lateinit var cancelBtn: Button
    lateinit var confirmBtn: Button

    fun init(binding: ActivitySqliteBinding) {
        nameInput = binding.nameEdit
        phoneInput = binding.phoneEdit
        operatorSheet = binding.operatorSheet
        confirmSheet = binding.confirmSheet
        cancelBtn = binding.cancel
        confirmBtn = binding.confirm
    }

    fun toggleVisible() {
        operatorSheet.visibility =
            if (operatorSheet.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        confirmSheet.visibility =
            if (confirmSheet.visibility == View.VISIBLE) View.GONE else View.VISIBLE
    }

    fun clearInput() {
        nameInput.setText("")
        phoneInput.setText("")
    }
}