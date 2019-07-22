package com.rviannaoliveira.varchitecturecomponentsmvvm.di

import android.app.Application
import com.rviannaoliveira.varchitecturecomponentsmvvm.AppApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules =[
    AndroidSupportInjectionModule::class,
    ActivityBindingModule::class,
    AppModule::class,
    NetworkModule::class,
    ViewModelModule::class])
interface AppComponent : AndroidInjector<AppApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}