package top.chilfish.learn.fragments;

import static top.chilfish.learn.activities.MainActivity.BROADCAST_ACTION;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import top.chilfish.learn.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

  private FragmentSecondBinding binding;

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      ViewGroup container,
      Bundle savedInstanceState
  ) {
    binding = FragmentSecondBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    binding.buttonSecondSend.setOnClickListener(v -> {
      Intent intent = new Intent(BROADCAST_ACTION);
      Bundle bundle = new Bundle();
      bundle.putString("data", "Hello World");
      intent.putExtras(bundle);

      Log.d("broadcast", "onViewCreated: " + bundle.getString("data"));

      LocalBroadcastManager
          .getInstance(requireActivity())
          .sendBroadcast(intent);
    });
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}