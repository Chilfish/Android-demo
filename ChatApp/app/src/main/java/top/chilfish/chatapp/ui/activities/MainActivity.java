package top.chilfish.chatapp.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import top.chilfish.chatapp.R;
import top.chilfish.chatapp.entity.ChatItem;
import top.chilfish.chatapp.entity.Profile;
import top.chilfish.chatapp.helper.JsonParser;
import top.chilfish.chatapp.helper.LoadFile;
import top.chilfish.chatapp.ui.fragments.ChatListFragment;
import top.chilfish.chatapp.ui.fragments.ProfileFragment;

public class MainActivity extends BaseActivity {
  private static final String TAG = "MainActivity";
  private ChatListFragment chatListFragment;

  private ProfileFragment profileFragment;

  private List<ChatItem> chatItems;

  private Profile profile;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    fetchChatList();
    profile = Profile.load();
    Log.d(TAG, "onCreate: " + profile);

    chatListFragment = new ChatListFragment(chatItems);
    profileFragment = new ProfileFragment(profile);
    navChange();
  }

  @SuppressLint("NonConstantResourceId")
  void navChange() {
    BottomNavigationView BtnNav = findViewById(R.id.nav_btn);

    final int id = R.id.frag_main;

    BtnNav.setOnItemSelectedListener(item -> {
      switch (item.getItemId()) {
        case R.id.nav_home:
          break;
        case R.id.nav_chat:
          replaceFragment(chatListFragment, id);
          break;
        case R.id.nav_settings:
          replaceFragment(profileFragment, id);
          break;
      }
      return true;
    });

    replaceFragment(chatListFragment, id);
  }

  private void replaceFragment(Fragment fragment, int id) {
    getSupportFragmentManager().beginTransaction()
        .replace(id, fragment)
        .commit();
  }

  private void fetchChatList() {
    try {
      String json = LoadFile.assetsString("chatList.json");
      chatItems = JsonParser.ChatList(json);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
