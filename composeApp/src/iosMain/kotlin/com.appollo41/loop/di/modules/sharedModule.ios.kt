package com.appollo41.loop.di.modules

import com.appollo41.loop.db.AppDatabase
import com.appollo41.loop.db.getDatabaseBuilder
import org.koin.dsl.module

actual fun platformModule() = module {
    single<AppDatabase> { getDatabaseBuilder() }
}