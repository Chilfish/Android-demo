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
      "1681142224000", "1681142464000", "1681142524000", "1681142704000", "1681143124000",
      "1681143304000", "1681143484000", "1681143604000", "1681143904000", "1681144324000"
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
