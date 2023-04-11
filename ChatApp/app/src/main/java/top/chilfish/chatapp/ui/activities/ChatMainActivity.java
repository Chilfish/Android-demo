package top.chilfish.chatapp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import top.chilfish.chatapp.R;
import top.chilfish.chatapp.entity.Message;
import top.chilfish.chatapp.helper.JsonParser;
import top.chilfish.chatapp.ui.fragments.ChatBarFragment;
import top.chilfish.chatapp.ui.fragments.MessageFragment;

public class ChatMainActivity extends BaseActivity {

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
    String chatAvatar = chatBundle.getString("chatAvatar");

    chatUid = chatBundle.getString("chatUid");
    curUid = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        .getString("uid", "");

    fetchMessage();
    mMessageFragment = new MessageFragment(MESSAGE_ITEMS);
    mSendButton = findViewById(R.id.btn_send);
    mMessageInput = findViewById(R.id.chat_input);

    replaceFragment(new ChatBarFragment(name, chatAvatar), R.id.frag_chat_bar);
    replaceFragment(mMessageFragment, R.id.frag_messages);

    //  TODO: add send message to server and local storage, meanwhile update the chat list
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
  void fetchMessage() {
    JsonParser jsonParser = new JsonParser();
    StringBuilder json = new StringBuilder();

    try (InputStream inputStream = getApplicationContext().getAssets().open("messages.json")) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      String line;
      while ((line = reader.readLine()) != null) {
        json.append(line);
      }

      MESSAGE_ITEMS = jsonParser.Messages(json.toString(), curUid)
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