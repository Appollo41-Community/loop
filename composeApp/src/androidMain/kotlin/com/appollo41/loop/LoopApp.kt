package com.appollo41.loop

import android.app.Application
import com.appollo41.loop.di.initKoin
import org.koin.android.ext.koin.androidContext

class LoopApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@LoopApp)
        }
    }
}