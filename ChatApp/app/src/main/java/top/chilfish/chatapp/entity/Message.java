package top.chilfish.chatapp.entity;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Message implements Serializable {
  private String mContent; // 消息内容
  private String mReceiverId; // 接收者的id
  private String mSenderId; // 发送者的id
  private String mTime; // 时间戳

  private String id; // 消息id

  private boolean isRight; // 是否是左边的消息

  public Message(String content, String receiverId, String senderId, String time, String id, boolean isRight) {
    mContent = content;
    mReceiverId = receiverId;
    mSenderId = senderId;
    mTime = time;
    this.id = id;
    this.isRight = isRight;
  }

  public Message(String content, String receiverId, String senderId, String id) {
    mContent = content;
    mReceiverId = receiverId;
    mSenderId = senderId;
    mTime = String.valueOf(System.currentTimeMillis());
    this.id = id;
    this.isRight = true;
  }

  public Message() {
    mContent = "";
    mReceiverId = "";
    mSenderId = "";
    mTime = "";
    this.id = "";
    this.isRight = true;
  }

  public String getContent() {
    return mContent;
  }

  public void setContent(String content) {
    mContent = content;
  }

  public String getReceiverId() {
    return mReceiverId;
  }

  public void setReceiverId(String receiverId) {
    mReceiverId = receiverId;
  }

  public String getSenderId() {
    return mSenderId;
  }

  public void setSenderId(String senderId) {
    mSenderId = senderId;
  }

  public long getTime() {
    return Long.parseLong(mTime);
  }

  public String getTimeString() {
    return mTime;
  }

  public void setTime(String time) {
    mTime = time;
  }

  public boolean isRight() {
    return isRight;
  }

  public String isRightString() {
    return String.valueOf(isRight);
  }

  public void setRight(String isRight){
    this.isRight = Boolean.parseBoolean(isRight);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @NonNull
  @Override
  public String toString() {
    return "Message{" +
        "mContent='" + mContent + '\'' +
        ", mReceiverId='" + mReceiverId + '\'' +
        ", mSenderId='" + mSenderId + '\'' +
        ", mTime=" + mTime +
        ", isRight=" + isRight +
        '}';
  }

}
