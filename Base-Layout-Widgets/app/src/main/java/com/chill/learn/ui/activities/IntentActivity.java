package com.chill.learn.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.chill.learn.R;
import com.chill.learn.ui.views.UserInfo;

public class IntentActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_intent);

    setInfo();
  }

  private void setInfo() {
    Intent intent = getIntent();
    Bundle loginBundle = intent.getExtras();

    if (loginBundle == null) {
      Log.d("IntentActivity", "Bundle is null");
      return;
    }

    String username = loginBundle.getString("username");
    String password = loginBundle.getString("password");

    Log.d("IntentActivity", "Username: " + username + " Password: " + password);

    UserInfo res_name = findViewById(R.id.res_name);
    UserInfo res_password = findViewById(R.id.res_password);

    res_name.setValue(username);
    res_password.setValue(password);
  }
}
