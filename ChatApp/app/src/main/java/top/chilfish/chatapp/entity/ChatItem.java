package top.chilfish.chatapp.entity;

public class ChatItem {
  private String mContent;
  private String mTime;

  private Profile mProfile;

  private String profileJson; // 序列化 Profile

  public ChatItem(Profile profile, String content, String time) {
    mContent = content;
    mTime = time;
    mProfile = profile;
    profileJson = Profile.getJson(profile);
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

  public Profile getProfile() {
    return mProfile;
  }

  public String getProfileJson() {
    return profileJson;
  }
}
