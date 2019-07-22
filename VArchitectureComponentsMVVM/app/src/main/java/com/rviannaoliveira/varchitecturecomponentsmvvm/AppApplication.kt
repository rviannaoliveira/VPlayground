package com.rviannaoliveira.varchitecturecomponentsmvvm

import com.rviannaoliveira.varchitecturecomponentsmvvm.di.DaggerAppComponent
import android.app.Activity
import android.app.Application
import dagger.android.*
import javax.inject.Inject

class AppApplication : DaggerApplication(){
    private val applicationInjector = DaggerAppComponent.builder()
        .application(this)
        .build()

    override fun applicationInjector() = applicationInjector
}