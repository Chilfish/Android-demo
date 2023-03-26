package com.chill.learn.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.chill.learn.R;

public class TextFragment extends Fragment {

  public TextFragment() {
    // Required empty public constructor
  }

  public static TextFragment newInstance() {
    TextFragment fragment = new TextFragment();
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_text, container, false);
  }
}
