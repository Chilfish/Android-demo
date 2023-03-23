package com.chill.learn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chill.learn.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    loginCheck();
  }

  // TODO: more case on validating password
  private boolean isPasswordValid(String password) {
    return password != null && password.length() >= 8;
  }

  void loginCheck() {
    Button loginButton = findViewById(R.id.btn_login);
    TextInputEditText passwordInput = findViewById(R.id.password_input);

    loginButton.setOnClickListener(view -> {
      String password = Objects.requireNonNull(passwordInput.getText()).toString();
      Log.d("password", password);

      if (!isPasswordValid(password)) {
        return;
      }
      Intent intent = new Intent(this, ProductListActivity.class);
      Toast toast = Toast.makeText(getApplicationContext(),
          "Login Successful", Toast.LENGTH_SHORT);
      toast.show();
      startActivity(intent);
    });
  }
}
