package com.rviannaoliveira.vbasicmvvmsample.di

import com.rviannaoliveira.repository.marvel.di.NetworkModule
import com.rviannaoliveira.vbasicmvvmsample.presentation.HomeViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (NetworkModule::class)])
interface AppComponent {
    fun inject(viewModel: HomeViewModel)
}
