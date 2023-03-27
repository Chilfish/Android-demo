package com.chill.learn.entity;

public class ChatItem {
  private String mName;
  private String mContent;
  private String mTime;

  private int mAvatarId;

  public ChatItem(String name, String content, String time, int avatarId) {
    mName = name;
    mContent = content;
    mTime = time;
    mAvatarId = avatarId;
  }

  public String getName() {
    return mName;
  }

  public void setName(String name) {
    mName = name;
  }

  public String getContent() {
    return mContent;
  }

  public void setContent(String content) {
    mContent = content;
  }

  public String getTime() {
    return mTime;
  }

  public void setTime(String time) {
    mTime = time;
  }

  public int getAvatarId() {
    return mAvatarId;
  }

  public void setAvatarId(int avatarId) {
    mAvatarId = avatarId;
  }
}
