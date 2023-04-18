package top.chilfish.chatapp.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import top.chilfish.chatapp.R;
import top.chilfish.chatapp.databinding.FragmentProfileBinding;
import top.chilfish.chatapp.entity.Profile;
import top.chilfish.chatapp.helper.LoginCheck;
import top.chilfish.chatapp.ui.activities.ChatMainActivity;
import top.chilfish.chatapp.ui.activities.LoginActivity;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding> {
  private final String TAG = "ProfileFragment";

  private Profile mProfile;
  private boolean isContact;
  private Button logoutBtn;
  private Button sendMsgBtn;

  public ProfileFragment(Profile profile) {
    mProfile = profile;
  }

  public ProfileFragment(Profile profile, boolean isContact) {
    mProfile = profile;
    this.isContact = isContact;
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_profile;
  }

  @Override
  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    bindData(view);
    if (!isContact) {
      logoutEvent(view);
    } else {
      sendMessageEvent(view);
    }
  }

  @SuppressLint("SetTextI18n")
  private void bindData(View view) {
    logoutBtn = binding.profileLogout;
    sendMsgBtn = binding.profileSendMessage;

    try {
      Glide.with(view.getContext())
          .load(mProfile.getAvatar())
          .into(binding.profileAvatar);

      binding.profileName.setText(mProfile.getName());
      binding.profileUid.setText("uid: " + mProfile.getUid());
      binding.profileEmail.setText(mProfile.getEmail());
      binding.profileBio.setText(mProfile.getBio());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void logoutEvent(View view) {
    logoutBtn.setVisibility(View.VISIBLE);
    sendMsgBtn.setVisibility(View.GONE);

    logoutBtn.setOnClickListener(v -> {
      LoginCheck.logout(view.getContext());

      Intent intent = new Intent(view.getContext(), LoginActivity.class);
      startActivity(intent);
    });
  }

  private void sendMessageEvent(View view) {
    sendMsgBtn.setVisibility(View.VISIBLE);
    logoutBtn.setVisibility(View.GONE);

    sendMsgBtn.setOnClickListener(v -> {
      Bundle bundle = new Bundle();
      bundle.putSerializable("profile", mProfile);

      Intent intent = new Intent(view.getContext(), ChatMainActivity.class);
      intent.putExtras(bundle);
      startActivity(intent);
    });
  }
}
