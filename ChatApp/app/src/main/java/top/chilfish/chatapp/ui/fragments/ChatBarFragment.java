package top.chilfish.chatapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import top.chilfish.chatapp.R;
import top.chilfish.chatapp.entity.Profile;
import top.chilfish.chatapp.ui.activities.ProfileActivity;

public class ChatBarFragment extends Fragment {
  private final Profile profile;

  public ChatBarFragment(Profile profile) {
    this.profile = profile;
  }

  public ChatBarFragment() {
    this.profile = new Profile();
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
    name.setText(profile.getName());

    ImageView avatarView = view.findViewById(R.id.chat_avatar);
    Glide.with(view.getContext())
        .load(profile.getAvatar())
        .into(avatarView);

    avatarView.setOnClickListener(v -> {
      Bundle bundle = new Bundle();
      bundle.putSerializable("profile", profile);

      Intent intent = new Intent(view.getContext(), ProfileActivity.class);
      intent.putExtras(bundle);
      startActivity(intent);
    });

    ImageButton back = view.findViewById(R.id.btn_chat_back);
    back.setOnClickListener(v -> requireActivity().finish());

    return view;
  }
}
