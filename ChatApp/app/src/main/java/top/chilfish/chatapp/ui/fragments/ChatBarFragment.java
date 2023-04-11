package top.chilfish.chatapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import top.chilfish.chatapp.R;

public class ChatBarFragment extends Fragment {
  private final String username;
  private final String avatar;

  public ChatBarFragment(String username, String avatar) {
    this.username = username;
    this.avatar = avatar;
  }

  public ChatBarFragment() {
    this.username = "Chat";
    this.avatar = "https://p.chilfsih.top/avatar.webp";
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

    ImageView avatarView = view.findViewById(R.id.chat_avatar);
    Glide.with(view.getContext())
        .load(avatar)
        .into(avatarView);

    return view;
  }
}
