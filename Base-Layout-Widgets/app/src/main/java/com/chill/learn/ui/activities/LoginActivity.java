package com.chill.learn.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chill.learn.R;
import com.chill.learn.helper.LoginCheck;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
  private Button loginButton;
  private Button registerBtn;

  TextInputEditText passwordInput;
  TextInputEditText usernameInput;

  private String username;
  private String password;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    loginButton = findViewById(R.id.btn_login);
    registerBtn = findViewById(R.id.btn_register);
    passwordInput = findViewById(R.id.password_input);
    usernameInput = findViewById(R.id.username_input);

    loginCheck();
  }

  // TODO: more case on validating password
  private boolean isPasswordInvalid() {
    password = Objects.requireNonNull(passwordInput.getText()).toString();
    username = Objects.requireNonNull(usernameInput.getText()).toString();

    if (password.length() < 8) {
      Toast.makeText(this, "密码长度不足8位", Toast.LENGTH_SHORT).show();
      return true;
    }
    return false;
  }

  private void loginCheck() {
    if (LoginCheck.isLoggedIn(this)) {
      jump();
    }

    loginButton.setOnClickListener(view -> {
      if (isPasswordInvalid()) {
        return;
      }
      if (!LoginCheck.isPasswordVide(this, username, password)) {
        Toast toast = Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT);
        toast.show();
        return;
      }

      Toast toast = Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT);
      toast.show();
      jump();
    });

    registerBtn.setOnClickListener(view -> {
      if (isPasswordInvalid()) {
        return;
      }

      if (LoginCheck.isUserExists(username)) {
        Toast.makeText(this, "该用户已存在", Toast.LENGTH_SHORT).show();
        return;
      }

      LoginCheck.saveUser(this, username, password);

      Toast toast = Toast.makeText(this, "Register Successful", Toast.LENGTH_SHORT);
      toast.show();
      jump();
    });
  }

  private void jump() {
    Bundle data = new Bundle();
    data.putString("username", username);
    data.putString("password", password);
    Intent intent = new Intent(this, MainActivity.class);
    intent.putExtras(data);
    startActivity(intent);
    finish();
  }

  @Override
  public void onBackPressed() {
    Intent intent = new Intent(Intent.ACTION_MAIN);
    intent.addCategory(Intent.CATEGORY_HOME);
    startActivity(intent);
    finish();
  }

}