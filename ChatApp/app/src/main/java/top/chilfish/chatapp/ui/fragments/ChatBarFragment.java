package top.chilfish.chatapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import top.chilfish.chatapp.R;
import top.chilfish.chatapp.databinding.FragmentChatBarBinding;
import top.chilfish.chatapp.entity.Profile;
import top.chilfish.chatapp.ui.activities.ProfileActivity;

public class ChatBarFragment extends BaseFragment<FragmentChatBarBinding> {
  private final Profile profile;

  public ChatBarFragment(Profile profile) {
    this.profile = profile;
  }

  public ChatBarFragment() {
    this.profile = new Profile();
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_chat_bar;
  }

  @Override
  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    binding.chatBarName.setText(profile.getName());

    var avatarView = binding.chatAvatar;
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

    binding.btnChatBack.setOnClickListener(v -> requireActivity().finish());
  }

}
