package com.appollo41.loop.di.modules

import com.appollo41.loop.networking.di.networking
import org.koin.dsl.module

val sharedModule = module {
    includes(networking)
}