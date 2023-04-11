package top.chilfish.chatapp.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import top.chilfish.chatapp.R;
import top.chilfish.chatapp.entity.ChatItem;
import top.chilfish.chatapp.entity.Profile;
import top.chilfish.chatapp.helper.JsonParser;
import top.chilfish.chatapp.ui.fragments.ChatFragment;
import top.chilfish.chatapp.ui.fragments.ProfileFragment;

public class MainActivity extends BaseActivity {
  private static final String TAG = "MainActivity";
  private ChatFragment chatFragment;

  private ProfileFragment profileFragment;

  private List<ChatItem> chatItems;

  private Profile profile;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    fetchChatList();
    profile = Profile.load(this);
    Log.d(TAG, "onCreate: " + profile);

    chatFragment = new ChatFragment(chatItems);
    profileFragment = new ProfileFragment(profile);
    navChange();
  }

  @SuppressLint("NonConstantResourceId")
  void navChange() {
    BottomNavigationView BtnNav = findViewById(R.id.nav_btn);
    RelativeLayout toolbar = findViewById(R.id.toolbar);

    final int id = R.id.frag_main;

    BtnNav.setOnItemSelectedListener(item -> {
      switch (item.getItemId()) {
        case R.id.nav_home:
          toolbar.setVisibility(RelativeLayout.GONE);
          break;
        case R.id.nav_chat:
          replaceFragment(chatFragment, id);
          toolbar.setVisibility(RelativeLayout.VISIBLE);
          break;
        case R.id.nav_settings:
          replaceFragment(profileFragment, id);
          toolbar.setVisibility(RelativeLayout.GONE);
          break;
      }
      return true;
    });

    replaceFragment(chatFragment, id);
  }

  private void replaceFragment(Fragment fragment, int id) {
    getSupportFragmentManager().beginTransaction()
        .replace(id, fragment)
        .commit();
  }

  private void fetchChatList() {
    JsonParser jsonParser = new JsonParser();
    StringBuilder json = new StringBuilder();

    try (InputStream inputStream = getApplicationContext().getAssets().open("chatList.json")) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      String line;
      while ((line = reader.readLine()) != null) {
        json.append(line);
      }

      chatItems = jsonParser.ChatList(json.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
