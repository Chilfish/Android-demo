package top.chilfish.chatapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import top.chilfish.chatapp.R;

public class LoginActivity extends AppCompatActivity {
  private Button loginButton;
  private Button registerBtn;

  TextInputEditText passwordInput;
  TextInputEditText usernameInput;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    loginButton = findViewById(R.id.btn_login);
    registerBtn = findViewById(R.id.btn_register);

    passwordInput = findViewById(R.id.password_input);
    usernameInput = findViewById(R.id.username_input);


    loginButton.setOnClickListener(this::loginCheck);
    registerBtn.setOnClickListener(this::loginCheck);
  }

  // TODO: more case on validating password
  private boolean isPasswordValid(String password) {
    return password != null && password.length() >= 8;
  }

  void loginCheck(View view) {

    String password = Objects.requireNonNull(passwordInput.getText()).toString();
    String username = Objects.requireNonNull(usernameInput.getText()).toString();
    Log.d("Login!", String.format("%s, %s", username, password));

    if (!isPasswordValid(password)) {
      return;
    }

    Bundle data = new Bundle();
    data.putString("username", username);
    data.putString("password", password);

    Intent intent = new Intent(this, MainActivity.class);
    intent.putExtras(data);
    startActivity(intent);
  }

}