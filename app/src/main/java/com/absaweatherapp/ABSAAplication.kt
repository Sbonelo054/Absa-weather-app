package com.absaweatherapp

import android.app.Application
import com.absaweatherapp.dependencyinjection.repoModule
import com.absaweatherapp.dependencyinjection.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ABSAAplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ABSAAplication)
            modules(listOf(repoModule, viewModelModule))
        }
    }
}