package com.chill.learn.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.chill.learn.R;
import com.chill.learn.ui.fragments.PropsFragment;

public class PropsActivity extends AppCompatActivity {
  private int mCount = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_props);

    // 将计数器的值传递给子组件
    getSupportFragmentManager().beginTransaction()
        .add(R.id.frag_props, PropsFragment.newInstance(mCount))
        .commit();
  }

  // 处理子组件发送的事件
  public void onButtonClick() {
    mCount++;
    var fragment = (PropsFragment) getSupportFragmentManager().findFragmentById(R.id.frag_props);
    if (fragment != null) {
      fragment.updateCount(mCount);
    }
  }
}