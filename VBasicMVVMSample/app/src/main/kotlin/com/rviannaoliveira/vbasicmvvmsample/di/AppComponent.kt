package com.rviannaoliveira.vbasicmvvmsample.di

import android.support.v7.app.AppCompatActivity
import com.rviannaoliveira.repository.marvel.di.NetworkModule
import com.rviannaoliveira.vbasicmvvmsample.di.home.HomeComponent
import com.rviannaoliveira.vbasicmvvmsample.di.home.HomeModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent{
    fun inject(activity: AppCompatActivity)
}
