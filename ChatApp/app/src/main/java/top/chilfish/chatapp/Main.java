package top.chilfish.chatapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import top.chilfish.chatapp.ui.activities.LoginActivity;

public class Main extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString("uid", "0");
    editor.apply();

    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }
}
