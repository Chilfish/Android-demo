package top.chilfish.compose.data.fake

import top.chilfish.compose.data.ChatItem
import top.chilfish.compose.data.Message
import top.chilfish.compose.models.currUid

object Messages {
    private val messages = listOf(
        Message(
            receiverId = "1",
            senderId = "0",
            content = "Hello",
            timestamp = "1681142224000"
        ),
        Message(
            receiverId = "0",
            senderId = "2",
            content = "Hello",
            timestamp = "1681143224000"
        ),
        Message(
            receiverId = "0",
            senderId = "1",
            content = "Hi there!",
            timestamp = "1681142224000"
        ),
        Message(
            receiverId = "2",
            senderId = "0",
            content = "How are you?",
            timestamp = "1681142824000"
        ),
        Message(
            receiverId = "0",
            senderId = "3",
            content = "I'm doing well, thanks for asking.",
            timestamp = "1681143424000"
        ),
        Message(
            receiverId = "1",
            senderId = "0",
            content = "That's great to hear!",
            timestamp = "1681144024000"
        ),
        Message(
            receiverId = "0",
            senderId = "2",
            content = "What have you been up to lately?",
            timestamp = "1681144624000"
        ),
        Message(
            receiverId = "3",
            senderId = "0",
            content = "Just keeping busy with work and family.",
            timestamp = "1681145224000"
        ),
        Message(
            receiverId = "0",
            senderId = "1",
            content = "Sounds like you have a full schedule.",
            timestamp = "1681145924000"
        ),
        Message(
            receiverId = "0",
            senderId = "1",
            content = "How are you?",
            timestamp = "1681144224000"
        ),
        Message(
            receiverId = "2",
            senderId = "0",
            content = "I'm fine, thank you",
            timestamp = "1681145224000"
        ),
        Message(
            receiverId = "3",
            senderId = "0",
            content = "What are you doing?",
            timestamp = "1681146224000"
        ),
        Message(
            receiverId = "1",
            senderId = "0",
            content = "Just watching TV",
            timestamp = "1681147224000"
        ),
        Message(
            receiverId = "0",
            senderId = "3",
            content = "That sounds relaxing",
            timestamp = "1681148224000"
        ),
        Message(
            receiverId = "2",
            senderId = "0",
            content = "What about you?",
            timestamp = "1681149224000"
        ),
        Message(
            receiverId = "0",
            senderId = "1",
            content = "I'm studying for exams",
            timestamp = "1681150224000"
        ),
        Message(
            receiverId = "3",
            senderId = "0",
            content = "Good luck!",
            timestamp = "1681151224000"
        ),
        Message(
            receiverId = "1",
            senderId = "0",
            content = "Thanks!",
            timestamp = "1681152224000"
        ),
        Message(
            receiverId = "0",
            senderId = "2",
            content = "What are you up to?",
            timestamp = "1681153224000"
        )
    )

    val chatHistory: (String) -> List<Message> = { chatUid ->
        messages.filter {
            (it.senderId == chatUid && it.receiverId == currUid.value)
                    || (it.receiverId == chatUid && it.senderId == currUid.value)
        }
    }

    val lastChat: (String) -> ChatItem = { chatUid ->
        val lastMessage = chatHistory(chatUid).first()
        val profile = Accounts.find(chatUid)
        ChatItem(
            profile = profile,
            content = lastMessage.content,
            time = lastMessage.timestamp,
        )
    }
}