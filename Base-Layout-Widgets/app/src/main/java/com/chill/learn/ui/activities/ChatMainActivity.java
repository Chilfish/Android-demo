package com.chill.learn.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import com.chill.learn.R;
import com.chill.learn.entity.Message;
import com.chill.learn.ui.fragments.ChatBarFragment;
import com.chill.learn.ui.fragments.MessageFragment;
import com.chill.learn.ui.fragments.placeholder.Messages;

public class ChatMainActivity extends AppCompatActivity {

  private final String Tag = "ChatMainActivity";

  private EditText mMessageInput;
  private ImageButton mSendButton;

  private MessageFragment mMessageFragment;


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

    mMessageFragment = new MessageFragment(Messages.MESSAGE_ITEMS);
    mSendButton = findViewById(R.id.btn_send);
    mMessageInput = findViewById(R.id.chat_input);

    replaceFragment(new ChatBarFragment(name), R.id.frag_chat_bar);
    replaceFragment(mMessageFragment, R.id.frag_messages);

    mSendButton.setOnClickListener(v -> {
      String message = mMessageInput.getText().toString();
      if (message.isEmpty()) {
        return;
      }
      Message newMessage = new Message(message, "1", "2");
      mMessageFragment.onSendMessage(newMessage);
      mMessageInput.setText("");
    });
  }

  private void replaceFragment(Fragment fragment, int id) {
    getSupportFragmentManager().beginTransaction()
        .replace(id, fragment)
        .commit();
  }

  public interface SendMessage {
    void onSendMessage(Message message);
  }
}