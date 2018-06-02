package com.rviannaoliveira.vbasicmvvmsample

import android.app.Application
import com.rviannaoliveira.vbasicmvvmsample.di.AppInjector

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        AppInjector.initialize(this)
    }
}