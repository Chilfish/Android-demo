package com.chill.learn.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.chill.learn.R;
import com.chill.learn.ui.activities.PropsActivity;

public class PropsFragment extends Fragment {

  private int mCount;

  public static PropsFragment newInstance(int count) {
    PropsFragment fragment = new PropsFragment();
    Bundle args = new Bundle();
    args.putInt("count", count);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mCount = getArguments().getInt("count");
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_props, container, false);
    Button button = view.findViewById(R.id.btn_props);
    button.setText(String.valueOf(mCount));

    button.setOnClickListener(v -> ((PropsActivity) requireActivity()).onButtonClick());
    return view;
  }

  public void updateCount(int count) {
    mCount = count;
    Button btn = requireView().findViewById(R.id.btn_props);
    btn.setText(String.valueOf(mCount));
  }
}
