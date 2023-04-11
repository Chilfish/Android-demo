package top.chilfish.chatapp.entity;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
  private String mContent; // 消息内容
  private String mReceiverId; // 接收者的id
  private String mSenderId; // 发送者的id
  private String mTime; // 时间戳

  private final boolean isRight; // 是否是左边的消息

  public Message(String content, String receiverId, String senderId, String time, boolean isRight) {
    mContent = content;
    mReceiverId = receiverId;
    mSenderId = senderId;
    mTime = time;
    this.isRight = isRight;
  }

  public Message(String content, String receiverId, String senderId) {
    mContent = content;
    mReceiverId = receiverId;
    mSenderId = senderId;
    mTime = String.valueOf(System.currentTimeMillis());
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

  public void setTime(String time) {
    mTime = time;
  }

  //  时间戳转时间字符串，并格式化为 23:00
  public String getShotTime() {
    Date date = new Date(getTime());
    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

    return sdf.format(date);
  }

  //  时间戳转时间字符串，并格式化为 12-12 23:00
  public String getLongTime() {
    Date date = new Date(getTime());
    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");

    return sdf.format(date);
  }


  public boolean isRight() {
    return isRight;
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
