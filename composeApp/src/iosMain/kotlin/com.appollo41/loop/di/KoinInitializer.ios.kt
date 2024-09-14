package com.appollo41.loop.di

import com.appollo41.loop.di.modules.platformModule
import com.appollo41.loop.di.modules.sharedModule
import org.koin.core.context.startKoin

actual class KoinInitializer {
    actual fun init() {
        startKoin {
            modules(
                sharedModule, platformModule()
            )
        }
    }
}