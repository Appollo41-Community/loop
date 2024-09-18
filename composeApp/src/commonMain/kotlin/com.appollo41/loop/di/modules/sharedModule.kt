package com.appollo41.loop.di.modules

import com.appollo41.loop.networking.di.networking
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun platformModule(): Module

val sharedModule = module {
    includes(networking)
}