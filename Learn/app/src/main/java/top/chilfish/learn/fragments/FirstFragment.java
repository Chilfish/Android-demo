package top.chilfish.learn.fragments;

import static top.chilfish.learn.activities.MainActivity.BROADCAST_ACTION;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.fragment.NavHostFragment;

import top.chilfish.learn.R;
import top.chilfish.learn.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

  private FragmentFirstBinding binding;

  private Receiver mReceiver = new Receiver();

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      ViewGroup container,
      Bundle savedInstanceState
  ) {
    binding = FragmentFirstBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    binding.buttonFirst.setOnClickListener(view1 -> {
      SecondFragment s = new SecondFragment();

      getParentFragmentManager()
          .beginTransaction()
          .add(R.id.nav_host_fragment, s)
          .addToBackStack(null)
          .commit();
    });
  }


  @Override
  public void onResume() {
    super.onResume();
    IntentFilter filter = new IntentFilter(BROADCAST_ACTION);
    LocalBroadcastManager
        .getInstance(requireActivity())
        .registerReceiver(mReceiver, filter);
  }

  @Override
  public void onPause() {
    super.onPause();
    LocalBroadcastManager
        .getInstance(requireActivity())
        .unregisterReceiver(mReceiver);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

  class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
      String data = intent.getStringExtra("data");
      binding.textReceived.setText(data);
      Log.d("broadcast", "onReceive: " + data);

      Toast.makeText(context, "Received " + data, Toast.LENGTH_SHORT).show();
    }
  }
}

