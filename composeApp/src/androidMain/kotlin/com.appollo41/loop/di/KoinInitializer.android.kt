package com.appollo41.loop.di

import android.content.Context
import com.appollo41.loop.di.modules.platformModule
import com.appollo41.loop.di.modules.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

//actual class KoinInitializer(
//    private val context: Context
//) {
//    actual fun init() {
//        startKoin {
//            androidContext(context)
//            modules(
//                sharedModule, platformModule()
//            )
//        }
//    }
//}