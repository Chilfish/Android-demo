package top.chilfish.chatapp.helper;

import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import top.chilfish.chatapp.entity.ChatItem;
import top.chilfish.chatapp.entity.Message;

public class JsonParser {
  private static final String TAG = "JsonParser";

  public static List<Message> Messages(String json, String curUid) throws Exception {

    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode rootNode = objectMapper.readTree(json);
    List<Message> messages = new ArrayList<>();

    for (JsonNode node : rootNode) {
      String receiverId = node.get("receiverId").asText();
      String senderId = node.get("senderId").asText();
      String content = node.get("content").asText();
      String timestamp = node.get("timestamp").asText();

      Message message = new Message(content, receiverId, senderId, timestamp, senderId.equals(curUid));
      messages.add(message);
    }
    Log.d(TAG, curUid + " " + messages.get(0).getSenderId());
    Log.d(TAG, messages.size() + "");
    return messages;
  }

  public static List<ChatItem> ChatList(String json) throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode rootNode = objectMapper.readTree(json);
    List<ChatItem> chatItems = new ArrayList<>();

    for (JsonNode node : rootNode) {
      String uid = node.get("uid").asText();
      String name = node.get("name").asText();
      String avatar = node.get("avatar").asText();
      String lastMessage = node.get("lastMessage").asText();

      String lastMessageTime = TimeFormat.toString(Long.parseLong(node.get("lastTime").asText()),
          "MM-dd");

      ChatItem chatItem = new ChatItem(uid, name, avatar, lastMessage, lastMessageTime);
      chatItems.add(chatItem);
    }
    return chatItems;
  }
}
