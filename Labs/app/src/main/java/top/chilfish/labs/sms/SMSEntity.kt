package top.chilfish.labs.sms

import top.chilfish.labs.Diffable

data class SMSEntity(
    val address: String,
    val body: String,
) : Diffable {
    override fun itemId() = address.hashCode().toLong()
    override fun sameContent(other: Diffable) =
        other is SMSEntity && address == other.address && body == other.body
}
