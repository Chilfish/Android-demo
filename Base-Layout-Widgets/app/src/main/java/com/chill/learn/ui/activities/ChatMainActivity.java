package com.chill.learn.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import com.chill.learn.R;
import com.chill.learn.entity.Message;
import com.chill.learn.helper.JsonParser;
import com.chill.learn.ui.fragments.ChatBarFragment;
import com.chill.learn.ui.fragments.MessageFragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class ChatMainActivity extends AppCompatActivity {

  private final String Tag = "ChatMainActivity";
  private static List<Message> MESSAGE_ITEMS;

  private EditText mMessageInput;
  private ImageButton mSendButton;

  private MessageFragment mMessageFragment;

  private String curUid;
  private String chatUid;


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
    chatUid = chatBundle.getString("chatUid");
    curUid = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        .getString("uid", "");

    generateMessage();
    mMessageFragment = new MessageFragment(MESSAGE_ITEMS);
    mSendButton = findViewById(R.id.btn_send);
    mMessageInput = findViewById(R.id.chat_input);

    replaceFragment(new ChatBarFragment(name), R.id.frag_chat_bar);
    replaceFragment(mMessageFragment, R.id.frag_messages);

    //  TODO: add send message to server and local storage
    mSendButton.setOnClickListener(v -> {
      String message = mMessageInput.getText().toString();
      if (message.isEmpty()) {
        return;
      }
      Message newMessage = new Message(message, chatUid, curUid);
      mMessageFragment.onSendMessage(newMessage);
      mMessageInput.setText("");
    });
  }

  private void replaceFragment(Fragment fragment, int id) {
    getSupportFragmentManager().beginTransaction()
        .replace(id, fragment)
        .commit();
  }

  // get messages from json file
  // TODO: get json messages from server, and check the local storage if it is new
  void generateMessage() {
    JsonParser messageParser = new JsonParser();
    StringBuilder json = new StringBuilder();

    try (InputStream inputStream = getApplicationContext().getAssets().open("messages.json")) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      String line;
      while ((line = reader.readLine()) != null) {
        json.append(line);
      }

      MESSAGE_ITEMS = messageParser.Messages(json.toString(), curUid)
          .stream()
          .filter(this::messagesFilter)
          .collect(Collectors.toList());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private boolean messagesFilter(Message m) {
    return (m.getReceiverId().equals(chatUid) && m.getSenderId().equals(curUid)) ||
        (m.getReceiverId().equals(curUid) && m.getSenderId().equals(chatUid));
  }

  public interface SendMessage {
    void onSendMessage(Message message);
  }
}