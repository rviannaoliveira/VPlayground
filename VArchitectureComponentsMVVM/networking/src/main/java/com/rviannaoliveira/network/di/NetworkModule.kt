package com.rviannaoliveira.network.di

import br.com.uol.ps.pagvendas.coreshared.networking.NetworkResponseAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {
    val instance = module {
        single<Moshi> { Moshi.Builder().add(KotlinJsonAdapterFactory()).build() }

        single(MainApi) { "https://demo0483622.mockable.io/" }

        single<Interceptor>(LoggingInterceptor) {
            HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            }
        }

        single {
            provideOkHttpClient(
                loggingInterceptor = get(LoggingInterceptor)
            )
        }

        single{
            provideRetrofit(
                oktHttpClient = get(),
                moshi = get(),
                url = get(MainApi)
            )
        }

    }

    private fun provideRetrofit(
        oktHttpClient: OkHttpClient,
        moshi: Moshi,
        url: String
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(url)
            .client(oktHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(NetworkResponseAdapterFactory(moshi))
            .build()

    private fun provideOkHttpClient(
        loggingInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .readTimeout(3, TimeUnit.SECONDS)
            .connectTimeout(3, TimeUnit.SECONDS)
            .build()

    object LoggingInterceptor : Qualifier {
        override val value: QualifierValue
            get() = "LoggingInterceptor"
    }

    object MainApi : Qualifier {
        override val value: QualifierValue
            get() = "MainApi"
    }
}