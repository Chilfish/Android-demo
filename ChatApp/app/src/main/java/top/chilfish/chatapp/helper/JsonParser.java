package top.chilfish.chatapp.helper;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import top.chilfish.chatapp.entity.Message;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser {
  public List<Message> Messages(String json, String curUid) throws Exception {
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
    Log.d("UID", curUid);
    return messages;
  }
}
