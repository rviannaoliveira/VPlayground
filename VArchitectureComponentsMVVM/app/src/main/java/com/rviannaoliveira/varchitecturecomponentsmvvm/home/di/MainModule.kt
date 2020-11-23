package com.rviannaoliveira.varchitecturecomponentsmvvm.home.di

import com.rviannaoliveira.varchitecturecomponentsmvvm.di.AppModule
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.data.MainRepository
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.data.MainRepositoryImpl
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.data.service.MainService
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.domain.MainUseCaseImpl
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.domain.MainUsecase
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.presentation.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

object MainModule {
    internal val instance = module {
        factory {
            provideService( retrofit =  get() )
        }
        factory<MainRepository> { MainRepositoryImpl(get()) }
        factory<MainUsecase> { MainUseCaseImpl(get()) }
        viewModel { MainViewModel(get(),get(),get(AppModule.IOScheduler)) }
    }

    private fun provideService(retrofit: Retrofit): MainService {
        return retrofit.create(MainService::class.java)
    }
}