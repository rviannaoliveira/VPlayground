package com.rviannaoliveira.varchitecturecomponentsmvvm.di

import android.content.res.Resources
import com.rviannaoliveira.network.di.NetworkModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue
import org.koin.dsl.module

object AppModule {
    private val instance = module {
        single<Resources> { androidApplication().resources }
        single(IOScheduler) { Dispatchers.IO }
    }

    object IOScheduler : Qualifier {
        override val value: QualifierValue
            get() = "IOScheduler"
    }

    val modules = listOf(instance, NetworkModule.instance)
}