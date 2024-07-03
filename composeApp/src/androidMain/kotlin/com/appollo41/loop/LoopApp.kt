package com.appollo41.loop

import android.app.Application
import com.appollo41.loop.di.KoinInitializer

class LoopApp : Application() {
    override fun onCreate() {
        super.onCreate()
        KoinInitializer(applicationContext).init()
    }
}