package com.appollo41.loop.networking.di

import com.appollo41.loop.networking.SocketClient
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.WebSockets
import org.koin.dsl.module


// TODO Replace with proper Koin module
object NetworkingModule {
    val httpClient = HttpClient {
        install(WebSockets)
    }
}

val networking = module {
    single {
        HttpClient {
            install(WebSockets)
        }
    }

    single { SocketClient(url = "wss://relay.primal.net", get()) }
}
