package com.appollo41.loop.di

import com.appollo41.loop.di.modules.platformModule
import com.appollo41.loop.di.modules.sharedModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

//expect class KoinInitializer {
//    fun init()
//}

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(sharedModule, platformModule())
    }
}