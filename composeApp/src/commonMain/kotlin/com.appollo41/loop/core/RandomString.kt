package com.appollo41.loop.core

import kotlin.random.Random

fun generateRandomString(length: Int = 24): String {
    val charPool = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return (1..length)
        .map { Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")
}