package top.chilfish.chatapp.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import top.chilfish.chatapp.R;
import top.chilfish.chatapp.entity.ChatItem;
import top.chilfish.chatapp.helper.JsonParser;
import top.chilfish.chatapp.ui.fragments.ChatFragment;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";
  private ChatFragment chatFragment;

  private List<ChatItem> chatItems;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    fetchChatList();
    chatFragment = new ChatFragment(chatItems);
    navChange();
  }

  @SuppressLint("NonConstantResourceId")
  void navChange() {
    BottomNavigationView BtnNav = findViewById(R.id.nav_btn);
    final int id = R.id.frag_chat;

    BtnNav.setOnItemSelectedListener(item -> {
      switch (item.getItemId()) {
        case R.id.nav_home:
//          replaceFragment(new TextFragment("Home"), id);
          break;
        case R.id.nav_chat:
          replaceFragment(chatFragment, id);
          break;
        case R.id.nav_settings:
//          replaceFragment(new TextFragment("Settings"), id);
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
