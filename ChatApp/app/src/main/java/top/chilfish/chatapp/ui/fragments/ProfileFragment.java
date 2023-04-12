package top.chilfish.chatapp.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import top.chilfish.chatapp.R;
import top.chilfish.chatapp.entity.Profile;

public class ProfileFragment extends Fragment {
  private final String TAG = "ProfileFragment";

  private Profile mProfile;

  public ProfileFragment(Profile profile) {
    mProfile = profile;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_profile, container, false);
    Context context = view.getContext();

    bindData(view);
    return view;
  }

  @SuppressLint("SetTextI18n")
  private void bindData(View view) {
    ImageView avatar = view.findViewById(R.id.profile_avatar);
    TextView name = view.findViewById(R.id.profile_name);
    TextView uid = view.findViewById(R.id.profile_uid);
    TextView email = view.findViewById(R.id.profile_email);
    TextView bio = view.findViewById(R.id.profile_bio);

    try {
      Glide.with(view.getContext())
          .load(mProfile.getAvatar())
          .into(avatar);

      name.setText(mProfile.getName());
      uid.setText("uid: " + mProfile.getUid());
      email.setText(mProfile.getEmail());
      bio.setText(mProfile.getBio());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
