package com.appollo41.loop.networking

import com.appollo41.loop.core.generateRandomString
import com.appollo41.loop.networking.di.NetworkingModule
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.wss
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import io.ktor.websocket.send
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject

class SocketClient(
    private val url: String,
    private val httpClient: HttpClient = NetworkingModule.httpClient,
) {
    private val scope = CoroutineScope(Dispatchers.IO)

    private var session: WebSocketSession? = null
    private var sessionJob: Job? = null

    private val mutableIncomingMessagesSharedFlow = MutableSharedFlow<NostrIncomingMessage>()

    val incomingMessages = mutableIncomingMessagesSharedFlow.asSharedFlow()

    fun connect() {
        sessionJob = scope.launch {
            try {
                httpClient.wss(urlString = url) {
                    println("Connected to $url.")
                    session = this
                    incoming.receiveAsFlow()
                        .collect { frame ->
                            when (frame) {
                                is Frame.Text -> {
                                    val text = frame.readText()
                                    scope.launch {
                                        text.parseIncomingMessage()?.let {
                                            mutableIncomingMessagesSharedFlow.emit(value = it)
                                        }
                                    }
                                }

                                is Frame.Close -> {
                                    // TODO Handle remote close
                                }

                                else -> Unit
                            }
                        }
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun close() {
        scope.launch {
            try {
                session?.close()
                sessionJob?.cancel()
            } catch (e: Exception) {
                println("Error closing socket session.")
            }
        }
    }

    private suspend fun sendMessage(text: String): Boolean {
        return try {
            println("--> $text $url")
            session?.send(text)
            true
        } catch (e: Exception) {
            println("Error sending message: ${e.message}")
            false
        }
    }

    suspend fun sendREQ(subscriptionId: String, data: JsonObject): Boolean {
        val reqMessage = data.buildNostrREQMessage(subscriptionId)
        return sendMessage(text = reqMessage)
    }

    suspend fun sendCOUNT(data: JsonObject): String? {
//        val subscriptionId: UUID = UUID.randomUUID()
        val subscriptionId: String = generateRandomString()
        val reqMessage = data.buildNostrCOUNTMessage(subscriptionId)
        val success = sendMessage(text = reqMessage)
        return if (success) subscriptionId else null
    }

    suspend fun sendCLOSE(subscriptionId: String): Boolean {
        return sendMessage(text = subscriptionId.buildNostrCLOSEMessage())
    }

    suspend fun sendEVENT(signedEvent: JsonObject): Boolean {
        return sendMessage(text = signedEvent.buildNostrEVENTMessage())
    }

    suspend fun sendAUTH(signedEvent: JsonObject): Boolean {
        return sendMessage(text = signedEvent.buildNostrAUTHMessage())
    }
}
