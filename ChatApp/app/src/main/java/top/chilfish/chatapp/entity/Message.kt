package top.chilfish.chatapp.entity

import java.io.Serializable

class Message(
    var content: String = "",       // 消息内容
    var receiverId: String = "",    // 接收者的id
    var senderId: String = "",      // 发送者的id
    var id: String = "",            // 消息id
    var timeStamp: String = System.currentTimeMillis().toString(),    // 时间戳
    var isRight: Boolean = true,    // 是否是右边的消息
) : Serializable {
    val time: Long
        get() = timeStamp.toLong()

    var isRightString: String
        get() = isRight.toString()
        set(isRight) {
            this.isRight = isRight == "true"
        }
}