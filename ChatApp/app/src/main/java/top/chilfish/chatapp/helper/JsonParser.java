package top.chilfish.chatapp.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import top.chilfish.chatapp.entity.ChatItem;
import top.chilfish.chatapp.entity.Message;
import top.chilfish.chatapp.entity.Profile;

public class JsonParser {
  private static final String TAG = "JsonParser";

  public static List<Message> Messages(String json) throws Exception {

    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode rootNode = objectMapper.readTree(json);
    List<Message> messages = new ArrayList<>();

    for (JsonNode node : rootNode) {
      String receiverId = node.get("receiverId").asText();
      String senderId = node.get("senderId").asText();
      String content = node.get("content").asText();
      String timestamp = node.get("timestamp").asText();
      String id = node.get("id").asText();

      Message message = new Message(content, receiverId, senderId,
          timestamp, id, senderId.equals(Profile.load().getUid()));
      messages.add(message);
    }

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
      String email = node.get("email").asText();
      String bio = node.get("bio").asText();

      Profile profile = new Profile(uid, name, avatar, email, bio);

      String lastMessage = node.get("lastMessage").asText();
      String lastMessageTime = TimeFormat.toString(Long.parseLong(node.get("lastTime").asText()),
          "MM-dd");

      ChatItem chatItem = new ChatItem(profile, lastMessage, lastMessageTime);
      chatItems.add(chatItem);
    }
    return chatItems;
  }

  public static List<Profile> Profile(String json) throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode rootNode = objectMapper.readTree(json);
    List<Profile> profiles = new ArrayList<>();

    for (JsonNode node : rootNode) {
      String uid = node.get("uid").asText();
      String name = node.get("name").asText();
      String avatar = node.get("avatar").asText();
      String email = node.get("email").asText();
      String bio = node.get("bio").asText();

      Profile profile = new Profile(uid, name, avatar, email, bio);
      profiles.add(profile);
    }
    return profiles;
  }
}
