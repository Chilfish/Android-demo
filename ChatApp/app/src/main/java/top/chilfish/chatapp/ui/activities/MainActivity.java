package top.chilfish.chatapp.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import top.chilfish.chatapp.R;
import top.chilfish.chatapp.ui.fragments.ChatFragment;
import top.chilfish.chatapp.ui.fragments.placeholder.Chatters;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";
  private final ChatFragment chatFragment = new ChatFragment(Chatters.CHAT_ITEMS);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

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
}
