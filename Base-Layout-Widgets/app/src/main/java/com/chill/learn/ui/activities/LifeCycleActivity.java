package com.chill.learn.ui.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class LifeCycleActivity extends AppCompatActivity {
  private void log(String msg) {
    String TAG = "LifeCycleActivity";
    Log.d(TAG, msg);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    log("onCreate");
  }

  @Override
  protected void onStart() {
    super.onStart();
    log("onStart");
  }

  @Override
  protected void onResume() {
    super.onResume();
    log("onResume");
  }

  @Override
  protected void onPause() {
    super.onPause();
    log("onPause");
  }

  @Override
  protected void onStop() {
    super.onStop();
    log("onStop");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    log("onDestroy");
  }

  @Override
  protected void onRestart() {
    super.onRestart();
    log("onRestart");
  }
}
