package com.chill.learn.entity;

public class ActivityCard {
  private String title;
  private Class<?> activity;

  public ActivityCard(String title, Class<?> activity) {
    this.title = title;
    this.activity = activity;
  }

  public String getTitle() {
    return title;
  }

  public Class<?> getActivity() {
    return activity;
  }
}
