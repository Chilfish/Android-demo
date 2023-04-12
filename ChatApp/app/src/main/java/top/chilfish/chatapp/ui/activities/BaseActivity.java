package top.chilfish.chatapp.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

//    // 设置状态栏透明
//    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//    // 设置内容主体的顶部内边距
//    int statusBarHeight = getStatusBarHeight();
//    View mainContent = findViewById(android.R.id.content);
//    mainContent.setPadding(0, statusBarHeight, 0, 0);
  }

  // 获取状态栏高度
  private int getStatusBarHeight() {
    int statusBarHeight = 0;
    @SuppressLint({"InternalInsetResource", "DiscouragedApi"})
    int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
      statusBarHeight = getResources().getDimensionPixelSize(resourceId);
    }
    return statusBarHeight;
  }
}
