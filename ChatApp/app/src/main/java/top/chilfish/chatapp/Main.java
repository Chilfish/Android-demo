package top.chilfish.chatapp;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import top.chilfish.chatapp.helper.LoginCheck;
import top.chilfish.chatapp.ui.activities.LoginActivity;
import top.chilfish.chatapp.ui.activities.MainActivity;

public class Main extends Application {
  @SuppressLint("StaticFieldLeak")
  public static Context AppCONTEXT;

  @Override
  public void onCreate() {
    super.onCreate();
    AppCONTEXT = getApplicationContext();
    Intent intent;

    if (LoginCheck.isLoggedIn(this)) {
      intent = new Intent(AppCONTEXT, MainActivity.class);
    } else {
      intent = new Intent(AppCONTEXT, LoginActivity.class);
    }

    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }
}
