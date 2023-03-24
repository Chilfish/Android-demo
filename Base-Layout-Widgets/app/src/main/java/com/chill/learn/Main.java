package com.chill.learn;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.chill.learn.activities.LoginActivity;
import com.chill.learn.activities.MainActivity;

public class Main extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
    Log.d("HELLO", "HI~");
  }
}
