package top.chilfish.chatapp.helper;

import static top.chilfish.chatapp.Main.AppCONTEXT;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LoadFile {

  // load text file from assets
  public static String assetsString(String fileName) {
    StringBuilder fileString = new StringBuilder();

    try (InputStream inputStream = AppCONTEXT.getAssets().open(fileName)) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      String line;
      while ((line = reader.readLine()) != null) {
        fileString.append(line);
      }
      return fileString.toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }
}
