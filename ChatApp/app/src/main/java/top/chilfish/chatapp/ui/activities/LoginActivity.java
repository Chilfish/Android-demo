package top.chilfish.chatapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import top.chilfish.chatapp.R;
import top.chilfish.chatapp.database.MessageDB;
import top.chilfish.chatapp.entity.Profile;
import top.chilfish.chatapp.helper.LoginCheck;

public class LoginActivity extends BaseActivity {
  private Button loginButton;
  private Button registerBtn;

  private TextInputEditText passwordInput;
  private TextInputEditText usernameInput;

  private Profile mProfile;

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

    fetchProfile();
    loginCheck();
  }


  // TODO: more case on validating password
  private boolean isPasswordInvalid() {
    password = Objects.requireNonNull(passwordInput.getText()).toString();
    username = Objects.requireNonNull(usernameInput.getText()).toString();

    if (password.length() < 8) {
      Toast.makeText(this, "Password needs len 8", Toast.LENGTH_SHORT).show();
      return true;
    }
    return false;
  }

  private void loginCheck() {
    if (LoginCheck.isLoggedIn(this)) {
      jump();
      return;
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
        Toast.makeText(this, "User is existed", Toast.LENGTH_SHORT).show();
        return;
      }

      LoginCheck.saveUser(this, username, password);
      try (var db = new MessageDB(this)) {
        db.dropTable();
      } catch (Exception e) {
        e.printStackTrace();
      }

      Toast toast = Toast.makeText(this, "Register Successful", Toast.LENGTH_SHORT);
      toast.show();
      jump();
    });
  }

  private void jump() {
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
    finish();
  }

  @Override
  public void onBackPressed() {
    Intent intent = new Intent(Intent.ACTION_MAIN);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.addCategory(Intent.CATEGORY_HOME);
    startActivity(intent);
    finish();
  }

  //  TODO: fetch profile from server
  private void fetchProfile() {
    String avatar = "https://p.chilfish.top/avatar.webp";
    String name = "Chilfish";
    String bio = "hi, I'm Chilfish.";
    String email = "chill4fish@gmail.com";
    String uid = "0";

    mProfile = new Profile(uid, name, avatar, email, bio);
    mProfile.save2SP();
  }

}