package com.chill.learn;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.chill.learn.helper.LoginCheck;
import com.chill.learn.ui.activities.LoginActivity;
import com.chill.learn.ui.activities.MainActivity;

public class Main extends Application {
  @SuppressLint("StaticFieldLeak")
  public static Context AppCONTEXT;

  @Override
  public void onCreate() {
    super.onCreate();

    var pref = getSharedPreferences("UserData", Context.MODE_PRIVATE);
    AppCONTEXT = getApplicationContext();

    SharedPreferences.Editor editor = pref.edit();
    editor.putString("uid", "0").apply();

    Intent intent;
    if (LoginCheck.isLoggedIn(this)) {
      intent = new Intent(this, MainActivity.class);
    } else {
      intent = new Intent(this, LoginActivity.class);
    }

    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }
}
