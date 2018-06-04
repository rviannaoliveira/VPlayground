package com.rviannaoliveira.vbasicmvvmsample.di.home

import com.rviannaoliveira.repository.marvel.IMarvelApiRepository
import com.rviannaoliveira.repository.marvel.MarvelApiRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class HomeModule {
    @Provides
    fun provideRepository(marvelApiRepositoryImpl: MarvelApiRepositoryImpl) : IMarvelApiRepository = marvelApiRepositoryImpl
}