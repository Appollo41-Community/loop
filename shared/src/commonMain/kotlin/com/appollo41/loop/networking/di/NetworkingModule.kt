package com.appollo41.loop.networking.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.WebSockets


// TODO Replace with proper Koin module
object NetworkingModule {
    val httpClient = HttpClient {
        install(WebSockets)
    }
}

//val networking = module {
//    single {
//        HttpClient {
//            install(WebSockets)
//        }
//    }
//}
