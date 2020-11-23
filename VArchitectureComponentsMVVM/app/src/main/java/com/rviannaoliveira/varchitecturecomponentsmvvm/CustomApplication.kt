package com.rviannaoliveira.varchitecturecomponentsmvvm

import android.app.Application
import com.rviannaoliveira.varchitecturecomponentsmvvm.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CustomApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(level = Level.INFO)
            androidContext(this@CustomApplication)
            modules(AppModule.modules)
        }
    }
}