package com.lsroar.marvel

import android.app.Application
import com.lsroar.marvel.di.AppKoinInitialization
import com.lsroar.timbercrashlytics.TimberCrashlytics
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

class MyApplication : Application() {

    private lateinit var koinApplication: KoinApplication

    override fun onCreate() {
        super.onCreate()

        // Koin
        val modules = mutableListOf<Module>()
        koinApplication = startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MyApplication)
            modules(
                modules.asSequence()
                    .plus(AppKoinInitialization.getModules())
                    .toList()
            )
        }

        TimberCrashlytics.init()
    }
}
