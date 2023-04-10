package com.chill.learn.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.chill.learn.R;
import com.chill.learn.ui.fragments.ChatBarFragment;
import com.chill.learn.ui.fragments.MessageFragment;
import com.chill.learn.ui.fragments.placeholder.Messages;

public class ChatMainActivity extends AppCompatActivity {

  private final String Tag = "ChatMainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat_main);

    Intent intent = getIntent();
    Bundle chatBundle = intent.getExtras();

    if (chatBundle == null) {
      Log.d(Tag, "Bundle is null");
      return;
    }

    String name = chatBundle.getString("chatName");

    replaceFragment(new ChatBarFragment(name), R.id.frag_chat_bar);
    replaceFragment(new MessageFragment(Messages.MESSAGE_ITEMS), R.id.frag_messages);
  }

  private void replaceFragment(Fragment fragment, int id) {
    getSupportFragmentManager().beginTransaction()
        .replace(id, fragment)
        .commit();
  }
}