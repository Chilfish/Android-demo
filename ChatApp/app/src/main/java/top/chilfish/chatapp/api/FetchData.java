package top.chilfish.chatapp.api;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import top.chilfish.chatapp.database.ChatListDB;
import top.chilfish.chatapp.database.ContactDB;
import top.chilfish.chatapp.database.MessageDB;
import top.chilfish.chatapp.entity.ChatItem;
import top.chilfish.chatapp.entity.Message;
import top.chilfish.chatapp.entity.Profile;
import top.chilfish.chatapp.helper.JsonParser;
import top.chilfish.chatapp.helper.LoadFile;

public class FetchData {
  public static String simple(String urlString) throws IOException {
    URL url = new URL(urlString);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.setConnectTimeout(5000);
    connection.setReadTimeout(5000);
    connection.connect();

    int responseCode = connection.getResponseCode();
    if (responseCode == HttpURLConnection.HTTP_OK) {
      InputStream inputStream = connection.getInputStream();
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
      StringBuilder stringBuilder = new StringBuilder();
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        stringBuilder.append(line);
      }
      bufferedReader.close();
      inputStream.close();
      connection.disconnect();
      return stringBuilder.toString();
    } else {
      connection.disconnect();
      throw new IOException("Server returned HTTP response code: " + responseCode);
    }
  }

  private static List<Message> messages;

  private static List<ChatItem> chatItems;

  private static List<Profile> contacts;

  public static void Messages() {
    try {
      String json = LoadFile.assetsString("messages.json");
      messages = JsonParser.Messages(json);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void ChatList() {
    try {
      String json = LoadFile.assetsString("chatList.json");
      chatItems = JsonParser.ChatList(json);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void Contacts() {
    try {
      String json = LoadFile.assetsString("contacts.json");
      contacts = JsonParser.Profile(json);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void All() {
    ChatList();
    Contacts();
    Messages();
  }

  public static void saveToDB(Context context) {
    try {
      MessageDB messageDB = new MessageDB(context);
      for (Message message : messages) {
        messageDB.insert(message);
      }

      ChatListDB chatListDB = new ChatListDB(context);
      for (ChatItem chatItem : chatItems) {
        chatListDB.insert(chatItem);
      }

      ContactDB contactDB = new ContactDB(context);
      for (Profile contact : contacts) {
        contactDB.insert(contact);
      }

      messageDB.close();
      chatListDB.close();
      contactDB.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
