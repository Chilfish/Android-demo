package top.chilfish.chatapp.entity;

import static top.chilfish.chatapp.Main.AppCONTEXT;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Profile implements Serializable {
  private String uid;
  private String name;
  private String avatar;
  private String email;

  private String bio;

  public Profile() {
    uid = "0";
    name = "default";
    avatar = "https://p.chilfish.top/avatar.webp";
    email = "default@chilfish.top";
  }

  public Profile(String uid, String name, String avatar, String email, String bio) {
    this.uid = uid;
    this.name = name;
    this.avatar = avatar;
    this.email = email;
    this.bio = bio;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  @NonNull
  @Override
  public String toString() {
    return "Profile{" +
        "uid='" + uid + '\'' +
        ", name='" + name + '\'' +
        ", avatar='" + avatar + '\'' +
        ", email='" + email + '\'' +
        ", bio='" + bio + '\'' +
        '}';
  }

  // save profile to shared preference
  public void save() {
    SharedPreferences SP = AppCONTEXT.getSharedPreferences("profile", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = SP.edit();
    editor.putString("uid", uid);
    editor.putString("name", name);
    editor.putString("avatar", avatar);
    editor.putString("email", email);
    editor.putString("bio", bio);
    editor.apply();
  }

  // get profile from shared preference
  public static Profile load() {
    SharedPreferences SP = AppCONTEXT.getSharedPreferences("profile", Context.MODE_PRIVATE);
    String uid = SP.getString("uid", "0");
    String name = SP.getString("name", "default");
    String avatar = SP.getString("avatar", "https://p.chilfish.top/avatar.webp");
    String email = SP.getString("email", "default@chilfish.top");
    String bio = SP.getString("bio", "default bio");
    return new Profile(uid, name, avatar, email, bio);
  }
}
