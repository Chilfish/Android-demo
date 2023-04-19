package top.chilfish.chatapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import top.chilfish.chatapp.R
import top.chilfish.chatapp.entity.Profile
import top.chilfish.chatapp.helper.LoginCheck.isLoggedIn
import top.chilfish.chatapp.helper.LoginCheck.isPasswordVide
import top.chilfish.chatapp.helper.LoginCheck.saveUser
import java.util.Objects

class LoginActivity : AppCompatActivity() {
    private var loginButton: Button? = null
    private var registerBtn: Button? = null
    private var passwordInput: TextInputEditText? = null
    private var usernameInput: TextInputEditText? = null
    private var mProfile: Profile? = null
    private var username: String? = null
    private var password: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton = findViewById(R.id.btn_login)
        registerBtn = findViewById(R.id.btn_register)
        passwordInput = findViewById(R.id.password_input)
        usernameInput = findViewById(R.id.username_input)

        fetchProfile()
        loginCheck()
    }

    // TODO: more case on validating password
    private val isPasswordInvalid: Boolean
        get() {
            password = Objects.requireNonNull(passwordInput!!.text).toString()
            username = Objects.requireNonNull(usernameInput!!.text).toString()
            if (password!!.length < 8) {
                Toast.makeText(this, "Password needs len 8", Toast.LENGTH_SHORT).show()
                return true
            }
            return false
        }

    private fun loginCheck() {
        if (isLoggedIn(this)) {
            jump()
            return
        }
        loginButton!!.setOnClickListener { view: View? ->
            if (isPasswordInvalid) {
                return@setOnClickListener
            }
            if (!isPasswordVide(this, username!!, password!!)) {
                val toast = Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT)
                toast.show()
                return@setOnClickListener
            }
            val toast = Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT)
            toast.show()
            jump()
        }
        registerBtn!!.setOnClickListener {
            if (isPasswordInvalid) {
                return@setOnClickListener
            }
//            if (isUserExists(username!!)) {
//                Toast.makeText(this, "User is existed", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
            saveUser(this, username!!, password!!)
            val toast = Toast.makeText(this, "Register Successful", Toast.LENGTH_SHORT)
            toast.show()
            jump()
        }
    }

    private fun jump() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addCategory(Intent.CATEGORY_HOME)
        startActivity(intent)
        finish()
    }

    //  TODO: fetch profile from server
    private fun fetchProfile() {
        val avatar = "https://p.chilfish.top/avatar.webp"
        val name = "Chilfish"
        val bio = "hi, I'm Chilfish."
        val email = "chill4fish@gmail.com"
        val uid = "0"
        mProfile = Profile(uid, name, avatar, email, bio)
        mProfile!!.save2SP()
    }
}