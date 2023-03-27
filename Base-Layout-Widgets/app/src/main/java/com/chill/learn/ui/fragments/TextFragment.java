package com.chill.learn.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.chill.learn.R;

public class TextFragment extends Fragment {
  private String mText;

  public TextFragment(String text) {
    mText = text;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_text, container, false);

    TextView textView = view.findViewById(R.id.frag_text);
    textView.setText(mText);
    return view;
  }
}
