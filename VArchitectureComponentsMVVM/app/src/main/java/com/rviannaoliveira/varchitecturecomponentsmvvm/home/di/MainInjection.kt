package com.rviannaoliveira.varchitecturecomponentsmvvm.home.di

import androidx.lifecycle.ViewModel
import com.rviannaoliveira.varchitecturecomponentsmvvm.di.ViewModelKey
import com.rviannaoliveira.varchitecturecomponentsmvvm.di.scopes.ActivityScope
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.data.MainRepository
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.data.MainRepositoryImpl
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.data.service.MainService
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.data.service.MainServiceImpl
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.presentation.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainActivityModule {
    @Binds
    abstract fun providesRepository(repository: MainRepositoryImpl): MainRepository

    @ActivityScope
    @Binds
    abstract fun providesService(service : MainServiceImpl): MainService

    @ActivityScope
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindAuthViewModel(authViewModel: MainViewModel): ViewModel
}
