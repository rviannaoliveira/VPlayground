package com.rviannaoliveira.repository.marvel.di

import com.rviannaoliveira.repository.marvel.IMarvelApiRepository
import com.rviannaoliveira.repository.marvel.MarvelApiRepositoryImpl
import com.rviannaoliveira.repository.marvel.MarvelClient
import com.rviannaoliveira.repository.marvel.remote.service.MarvelService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class NetworkModule(private val apiUrl: String) {

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): MarvelService = retrofit.create(MarvelService::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(apiUrl)
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor({ chain -> MarvelClient.createParametersDefault(chain) })
                .build()
    }

    @Provides
    @Singleton
    fun providesInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    fun provideRepository(marvelApiRepositoryImpl: MarvelApiRepositoryImpl) : IMarvelApiRepository
            = marvelApiRepositoryImpl

}
