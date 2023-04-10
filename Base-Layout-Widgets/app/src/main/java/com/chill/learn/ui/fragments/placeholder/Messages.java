package com.chill.learn.ui.fragments.placeholder;

import com.chill.learn.entity.Message;

import java.util.ArrayList;
import java.util.List;

public class Messages {
  public static final List<Message> MESSAGE_ITEMS = new ArrayList<>();

  private static final int COUNT = 10;

  private static final String[] CONTENTS = {
      "Hello", "Hi", "How are you?", "I'm fine", "What's up?",
      "Nothing much", "What's new?", "Nothing much", "What's up?", "Nothing much"
  };

  private static final String[] RECEIVER_IDS = {
      "1", "2", "1", "1", "2", "1", "2", "1", "2", "1"
  };

  private static final String[] SENDER_IDS = {
      "2", "1", "2", "2", "1", "2", "1", "2", "1", "2"
  };

  private static final String[] TIMESTAMPS = {
      "1627912124000", "1627919164000", "1627926206000", "1627933247000", "1627940288000",
      "1627947328000", "1627954369000", "1627961410000", "1627968451000", "1627975492000"
  };

  private static final String CurrentUserId = "1";

  static {
    for (int i = 0; i < COUNT; i++) {
      boolean isRight = SENDER_IDS[i].equals(CurrentUserId);

      MESSAGE_ITEMS.add(new Message(CONTENTS[i], RECEIVER_IDS[i], SENDER_IDS[i], TIMESTAMPS[i], isRight));
    }
  }

  Messages() {
  }
}
