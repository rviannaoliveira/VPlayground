package com.rviannaoliveira.vbasicmvvmsample.di

import android.app.Application
import android.support.v7.app.AppCompatActivity
import com.rviannaoliveira.repository.marvel.ApiConstant
import com.rviannaoliveira.repository.marvel.di.NetworkModule
import com.rviannaoliveira.vbasicmvvmsample.AppApplication
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

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


@Singleton
@Component(modules = [AppModule::class,NetworkModule::class])
interface AppComponent{
    fun inject(activity: AppCompatActivity)
}

@Module
class AppModule(private val application: Application) {
    @Provides
    @Singleton
    fun providesApplication() : Application = application
}
