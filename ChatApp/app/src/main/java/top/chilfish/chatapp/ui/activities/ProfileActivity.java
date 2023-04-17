package top.chilfish.chatapp.ui.activities;

import android.os.Bundle;
import android.widget.ImageButton;

import top.chilfish.chatapp.R;
import top.chilfish.chatapp.entity.Profile;
import top.chilfish.chatapp.ui.fragments.ProfileFragment;

public class ProfileActivity extends BaseActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_contact_profile);

    Bundle profileBundle = getIntent().getExtras();

    if (profileBundle == null) {
      return;
    }
    Profile profile;

    try {
      profile = (Profile) profileBundle.getSerializable("profile");
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }

    ProfileFragment profileFragment = new ProfileFragment(profile, true);
    profileFragment.setArguments(profileBundle);

    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.frag_profile, profileFragment)
        .commit();

    ImageButton back = findViewById(R.id.btn_profile_back);
    back.setOnClickListener(v -> finish());
  }
}
