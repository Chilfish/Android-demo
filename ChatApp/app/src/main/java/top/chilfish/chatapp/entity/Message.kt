package top.chilfish.chatapp.entity

import java.io.Serializable

class Message : Serializable {
    // 消息内容
    var content: String

    // 接收者的id
    var receiverId: String

    // 发送者的id
    var senderId: String

    // 时间戳
    var timeString: String
        private set

    // 消息id
    var id: String

    // 是否是右边的消息
    var isRight: Boolean
        private set

    constructor(
        content: String,
        receiverId: String,
        senderId: String,
        time: String,
        id: String,
        isRight: Boolean
    ) {
        this.content = content
        this.receiverId = receiverId
        this.senderId = senderId
        this.timeString = time
        this.id = id
        this.isRight = isRight
    }

    constructor(content: String, receiverId: String, senderId: String, id: String) {
        this.content = content
        this.receiverId = receiverId
        this.senderId = senderId
        this.timeString = System.currentTimeMillis().toString()
        this.id = id
        this.isRight = true
    }

    constructor() {
        content = ""
        receiverId = ""
        senderId = ""
        timeString = ""
        id = ""
        isRight = true
    }

    val time: Long
        get() = timeString.toLong()

    fun setTime(time: String) {
        timeString = time
    }

    val isRightString: String
        get() = isRight.toString()

    fun setRight(isRight: String?) {
        this.isRight = java.lang.Boolean.parseBoolean(isRight)
    }

    override fun toString(): String {
        return "Message {" +
                " mContent= '$content'" +
                ", mReceiverId= receiverId" +
                ", mSenderId= $senderId" +
                ", mTime= $timeString" +
                ", isRight= $isRight }"
    }
}