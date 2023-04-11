package com.chill.learn.ui.fragments.placeholder;

import com.chill.learn.R;
import com.chill.learn.entity.ChatItem;

import java.util.ArrayList;
import java.util.List;

public class Chatters {
  public static final List<ChatItem> CHAT_ITEMS = new ArrayList<>();

  private static final int COUNT = 10;

  private static final String[] UID = {
      "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"
  };

  private static final String[] NAMES = {
      "John", "Jane", "Jack", "Jill", "Jenny", "Jen", "Jenifer", "Jeniffer", "Jeniffer", "Jeniffer"
  };

  private static final String[] MESSAGES = {
      "Hello", "Hi", "How are you?", "I'm fine", "What's up?", "Nothing much", "What's new?", "Nothing", "What's going on?", "Nothing"
  };

  private static final String[] TIME = {
      "12:00", "12:01", "12:02", "12:03", "12:04", "12:05", "12:06", "12:07", "12:08", "12:09"
  };

  private static final int AVATAR = R.drawable.avatar;

  static {
    for (int i = 0; i < COUNT; i++) {
      CHAT_ITEMS.add(new ChatItem(UID[i], NAMES[i], MESSAGES[i], TIME[i], AVATAR));
    }
  }

  public Chatters() {
  }
}
