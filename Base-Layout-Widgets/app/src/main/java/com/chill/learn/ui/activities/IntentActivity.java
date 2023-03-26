package com.chill.learn.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.chill.learn.R;
import com.chill.learn.ui.fragments.HomeFragment;
import com.chill.learn.ui.fragments.TextFragment;
import com.chill.learn.ui.views.UserInfo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class IntentActivity extends LifeCycleActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_intent);

    setInfo();
    navChange();
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

  @SuppressLint("NonConstantResourceId")
  void navChange() {
    BottomNavigationView BtnNav = findViewById(R.id.nav_btn);
    BtnNav.setOnItemSelectedListener(item -> {
      switch (item.getItemId()) {
        case R.id.nav_home:
          replaceFragment(new HomeFragment());
          break;
        case R.id.nav_settings:
          replaceFragment(new TextFragment());
          break;
      }
      return true;
    });
    replaceFragment(new HomeFragment());
  }

  private void replaceFragment(Fragment fragment) {
    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.frag_nav, fragment)
        .commit();
  }
}
