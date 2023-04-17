package top.chilfish.chatapp.ui.activities;

import static top.chilfish.chatapp.Main.AppCONTEXT;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.UUID;

import top.chilfish.chatapp.R;
import top.chilfish.chatapp.database.MessageDB;
import top.chilfish.chatapp.entity.Message;
import top.chilfish.chatapp.entity.Profile;
import top.chilfish.chatapp.ui.fragments.ChatBarFragment;
import top.chilfish.chatapp.ui.fragments.MessageFragment;

public class ChatMainActivity extends BaseActivity {

  private final String Tag = "ChatMainActivity";
  private List<Message> messageList;

  private EditText mMessageInput;
  private ImageButton mSendButton;

  private MessageFragment mMessageFragment;

  private String curUid;
  private String chatUid;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat_main);

    Bundle chatBundle = getIntent().getExtras();

    if (chatBundle == null) {
      Log.d(Tag, "Bundle is null");
      return;
    }
    Profile profile;

    try {
      profile = (Profile) chatBundle.getSerializable("profile");
      Log.d(Tag, "profile: " + profile.toString());
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }

    chatUid = profile.getUid();
    curUid = AppCONTEXT.getSharedPreferences("profile", Context.MODE_PRIVATE)
        .getString("uid", "");

    loadMessage();

    mMessageFragment = new MessageFragment(messageList);
    mSendButton = findViewById(R.id.btn_send);
    mMessageInput = findViewById(R.id.chat_input);

    replaceFragment(new ChatBarFragment(profile), R.id.frag_chat_bar);
    replaceFragment(mMessageFragment, R.id.frag_messages);

    sendMessage();
  }

  private void replaceFragment(Fragment fragment, int id) {
    getSupportFragmentManager().beginTransaction()
        .replace(id, fragment)
        .commit();
  }

  void loadMessage() {
    try (MessageDB db = new MessageDB(this)) {
      messageList = db.getById(chatUid);
      Log.d(Tag, "Messages: " + messageList.size());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //  TODO: add send message to server and local storage, meanwhile update the chat list
  private void sendMessage() {
    mSendButton.setOnClickListener(v -> {
      String message = mMessageInput.getText().toString();
      if (message.isEmpty()) {
        return;
      }
      Message newMessage = new Message(message, chatUid, curUid, UUID.randomUUID().toString());
      mMessageFragment.onSendMessage(newMessage);
      mMessageInput.setText("");
    });
  }

  public interface OnSendMessage {
    void onSendMessage(Message message);
  }

  @Override
  public void onBackPressed() {
    Intent intent = new Intent(this, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent);
    finish();
  }
}