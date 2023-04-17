package top.chilfish.chatapp.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import top.chilfish.chatapp.R;
import top.chilfish.chatapp.api.FetchData;
import top.chilfish.chatapp.database.ChatListDB;
import top.chilfish.chatapp.database.ContactDB;
import top.chilfish.chatapp.entity.ChatItem;
import top.chilfish.chatapp.entity.Profile;
import top.chilfish.chatapp.helper.LoginCheck;
import top.chilfish.chatapp.ui.fragments.ChatListFragment;
import top.chilfish.chatapp.ui.fragments.ContactFragment;
import top.chilfish.chatapp.ui.fragments.ProfileFragment;

public class MainActivity extends BaseActivity {
  private static final String TAG = "MainActivity";
  private ChatListFragment chatListFragment;

  private ProfileFragment profileFragment;

  private ContactFragment contactFragment;

  private List<ChatItem> chatItems;

  private List<Profile> contacts;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    checkLogin();

    FetchData.All();

    SharedPreferences pref = getSharedPreferences("status", MODE_PRIVATE);
    if (pref.getBoolean("isFirst", true)) {
      FetchData.saveToDB(this);
      pref.edit().putBoolean("isFirst", false).apply();
    }

    loadDB();

    chatListFragment = new ChatListFragment(chatItems);
    profileFragment = new ProfileFragment(Profile.load());
    contactFragment = new ContactFragment(contacts);

    navChange();
  }

  @SuppressLint("NonConstantResourceId")
  private void navChange() {
    BottomNavigationView BtnNav = findViewById(R.id.nav_btn);

    final int id = R.id.frag_main;

    BtnNav.setOnItemSelectedListener(item -> {
      final int itemId = item.getItemId();

      if (itemId == R.id.nav_chat) {
        replaceFragment(chatListFragment, id);
      } else if (itemId == R.id.nav_profile) {
        replaceFragment(profileFragment, id);
      } else if (itemId == R.id.nav_contact) {
        replaceFragment(contactFragment, id);
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

  private void checkLogin() {
    if (!LoginCheck.isLoggedIn(this)) {
      Intent intent = new Intent(this, LoginActivity.class);
      startActivity(intent);
      finish();
    }
  }

  @Override
  public void onBackPressed() {
    Intent intent = new Intent(Intent.ACTION_MAIN);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.addCategory(Intent.CATEGORY_HOME);
    startActivity(intent);
    finish();
  }

  private void loadDB() {
    try {
      ChatListDB chatListDB = new ChatListDB(this);
      chatItems = chatListDB.getAll();

      ContactDB contactDB = new ContactDB(this);
      contacts = contactDB.getAll();

      chatListDB.close();
      contactDB.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
