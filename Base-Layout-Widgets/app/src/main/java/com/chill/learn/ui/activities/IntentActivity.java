package com.chill.learn.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.chill.learn.R;
import com.chill.learn.ui.fragments.ChatFragment;
import com.chill.learn.ui.fragments.TextFragment;
import com.chill.learn.ui.fragments.placeholder.Chatters;
import com.chill.learn.ui.views.UserInfo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class IntentActivity extends LifeCycleActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_intent);

    navChange();
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

    UserInfo res_name = findViewById(R.id.res_name);
    UserInfo res_password = findViewById(R.id.res_password);

    res_name.setValue(username);
    res_password.setValue(password);
  }

  @SuppressLint("NonConstantResourceId")
  void navChange() {
    BottomNavigationView BtnNav = findViewById(R.id.nav_btn);
    final int id = R.id.frag_nav;

    BtnNav.setOnItemSelectedListener(item -> {
      switch (item.getItemId()) {
        case R.id.nav_home:
          replaceFragment(new TextFragment("Home"), id);
          break;
        case R.id.nav_chat:
          replaceFragment(new ChatFragment(Chatters.CHAT_ITEMS), id);
          break;
        case R.id.nav_settings:
          replaceFragment(new TextFragment("Settings"), id);
          break;
      }
      return true;
    });
    replaceFragment(new TextFragment("Home"), id);
  }

  private void replaceFragment(Fragment fragment, int id) {
    getSupportFragmentManager().beginTransaction()
        .replace(id, fragment)
        .commit();
  }
}
