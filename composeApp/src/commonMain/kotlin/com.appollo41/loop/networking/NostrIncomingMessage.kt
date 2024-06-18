package com.appollo41.loop.networking

sealed class NostrIncomingMessage {

    data class EventMessage(
        val subscriptionId: String,
        val nostrEvent: NostrEvent? = null,
    ) : NostrIncomingMessage()

    data class EoseMessage(
        val subscriptionId: String,
    ) : NostrIncomingMessage()

    data class OkMessage(
        val eventId: String,
        val success: Boolean,
        val message: String? = null,
    ) : NostrIncomingMessage()

    data class NoticeMessage(
        val subscriptionId: String? = null,
        val message: String? = null,
    ) : NostrIncomingMessage()

    data class AuthMessage(
        val challenge: String,
    ) : NostrIncomingMessage()

    data class CountMessage(
        val subscriptionId: String,
        val count: Int,
    ) : NostrIncomingMessage()
}
