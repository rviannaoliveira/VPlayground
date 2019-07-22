package com.rviannaoliveira.varchitecturecomponentsmvvm.di

import android.app.Application
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideResources(application : Application): Resources = application.resources

    @Provides
    @Singleton
    @Named("IOScheduler")
    fun provideIOScheduler(): Scheduler = Schedulers.io()

    @Provides
    @Singleton
    @Named("MainScheduler")
    fun provideMainScheduler(): Scheduler = AndroidSchedulers.mainThread()
}