package top.chilfish.chatapp.entity;

public class ChatItem {
  private String mUid;
  private String mName;
  private String mContent;
  private String mTime;

  private String mAvatar;

  public ChatItem(String uid, String name, String avatar, String content, String time) {
    mUid = uid;
    mName = name;
    mContent = content;
    mTime = time;
    mAvatar = avatar;
  }

  public String getUid() {
    return mUid;
  }

  public void setUid(String uid) {
    mUid = uid;
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

  public String getAvatar() {
    return mAvatar;
  }

  public void setAvatarId(String avatar) {
    mAvatar = avatar;
  }
}
