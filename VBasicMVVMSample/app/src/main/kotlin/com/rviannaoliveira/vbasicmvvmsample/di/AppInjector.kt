package com.rviannaoliveira.vbasicmvvmsample.di

import com.rviannaoliveira.repository.marvel.ApiConstant
import com.rviannaoliveira.repository.marvel.di.NetworkModule
import com.rviannaoliveira.vbasicmvvmsample.AppApplication

object AppInjector {
    lateinit var component: AppComponent
        private set

    fun initialize(appApplication: AppApplication) {
        component = DaggerAppComponent
                .builder()
                .appModule(AppModule(appApplication))
                .networkModule(NetworkModule(ApiConstant.API_MARVEL_URL))
                .build()
    }
}
