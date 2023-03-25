package com.chill.learn.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chill.learn.R;

public class UserInfo extends LinearLayout {
  public UserInfo(Context context, AttributeSet attrs) {
    super(context, attrs);
    LayoutInflater.from(context).inflate(R.layout.view_user, this, true);

    TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.UserInfo);
    String hint = ta.getString(R.styleable.UserInfo_hint);
    String value = ta.getString(R.styleable.UserInfo_value);
    ta.recycle();

    TextView hintView = findViewById(R.id.hint);

    hintView.setText(hint);
    setValue(value);
  }

  public void setValue(String value) {
    TextView valueView = findViewById(R.id.value);
    valueView.setText(value);
  }

  public String getValue() {
    TextView valueView = findViewById(R.id.value);
    return valueView.getText().toString();
  }
}