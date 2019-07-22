package com.rviannaoliveira.varchitecturecomponentsmvvm.di

import com.rviannaoliveira.varchitecturecomponentsmvvm.di.scopes.ActivityScope
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.di.MainActivityModule
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.presentation.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    internal abstract fun mainActivity(): MainActivity
}