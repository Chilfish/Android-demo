package top.chilfish.chatapp.helper;

import static top.chilfish.chatapp.Main.AppCONTEXT;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class LoginCheck {
  private static final String CSV_File_Name = "users.csv";
  private static final String Status_Key = "status";
  private static final String Pref_Key = "UserData";
  private static final String CSV_SEPARATOR = ",";
  private static final String SALT = "ChILL_SALT";

  private static final String TAG = "LoginCheck";

  public static boolean isLoggedIn(Context context) {
    return context.getSharedPreferences(Pref_Key, Context.MODE_PRIVATE)
        .getBoolean(Status_Key, false);
  }

  public static void logout(Context context) {
    var pref = context.getSharedPreferences(Pref_Key, Context.MODE_PRIVATE);
    pref.edit().putBoolean(Status_Key, false).apply();
  }

  public static void saveUser(Context context, String username, String password) {
    String csvRow = username + CSV_SEPARATOR +
        encryptPassword(password) + CSV_SEPARATOR + true;

    context.getSharedPreferences(Pref_Key, Context.MODE_PRIVATE)
        .edit()
        .putBoolean(Status_Key, true)
        .apply();

    try {
      FileWriter writer = new FileWriter(getCsvFile(), true);
      writer.write(csvRow);
      writer.append('\n');
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static String encryptPassword(String password) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update((password + SALT).getBytes());
      byte[] digest = md.digest();
      return Base64.encodeToString(digest, Base64.NO_WRAP);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return "";
  }

  private static File getCsvFile() throws IOException {
    File file = new File(AppCONTEXT.getFilesDir(), CSV_File_Name);
    if (!file.exists()) {
      var status = file.createNewFile();
      if (!status) {
        Log.d(TAG, "getCsvFile: 创建文件失败");
      }
    }
    return file;
  }

  private static List<String> getCsvLines() {
    var lines = new ArrayList<String>();

    try {
      File file = getCsvFile();
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String line;
      while ((line = reader.readLine()) != null) {
        lines.add(line);
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return lines;
  }

  public static boolean isUserExists(String username) {
    for (String line : Objects.requireNonNull(getCsvLines())) {
      line = line.split(CSV_SEPARATOR)[0];

      Log.d(TAG, "isUserExists: " + line);
      if (line.equals(username)) {
        return true;
      }
    }
    return false;
  }

  public static boolean isPasswordVide(Context context, String username, String password) {
    String csvRow = username + CSV_SEPARATOR + encryptPassword(password);

    for (String line : Objects.requireNonNull(getCsvLines())) {
      var l = line.split(CSV_SEPARATOR);
      line = l[0] + CSV_SEPARATOR + l[1];

      if (line.equals(csvRow)) {
        context.getSharedPreferences(Pref_Key, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(Status_Key, true)
            .apply();
        return true;
      }
    }
    return false;
  }
}
