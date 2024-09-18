package com.appollo41.loop.networking

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import com.appollo41.loop.networking.serialization.NostrJson

fun NostrEventKind.isUnknown() = this == NostrEventKind.Unknown

fun NostrEventKind.isNotUnknown() = !isUnknown()

fun JsonObject?.asNostrEventOrNull(): NostrEvent? {
    return try {
        if (this != null) NostrJson.decodeFromJsonElement(this) else null
    } catch (error: IllegalArgumentException) {
        println(error)
        null
    }
}
