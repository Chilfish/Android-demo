package top.chilfish.chatapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import top.chilfish.chatapp.R;

public class ChatBarFragment extends Fragment {
  private final String username;

  public ChatBarFragment(String username) {
    this.username = username;
  }

  public ChatBarFragment() {
    this.username = "Chat";
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_chat_bar, container, false);
    TextView name = view.findViewById(R.id.chat_bar_name);
    name.setText(username);
    return view;
  }
}
