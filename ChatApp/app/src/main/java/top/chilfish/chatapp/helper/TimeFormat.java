package top.chilfish.chatapp.helper;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public  class TimeFormat {
  public static String toString(long time, String format) {
    Date date = new Date(time);
    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(date);
  }
}
