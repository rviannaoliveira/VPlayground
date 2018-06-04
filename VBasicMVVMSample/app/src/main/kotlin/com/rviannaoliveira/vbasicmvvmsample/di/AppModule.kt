package com.rviannaoliveira.vbasicmvvmsample.di

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(private val application: Application) {
    @Provides
    @Singleton
    fun providesApplication() : Application = application
}
