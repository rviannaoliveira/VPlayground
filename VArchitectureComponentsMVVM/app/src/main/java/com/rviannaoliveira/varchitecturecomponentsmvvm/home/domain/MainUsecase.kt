package com.rviannaoliveira.varchitecturecomponentsmvvm.home.domain

import com.rviannaoliveira.varchitecturecomponentsmvvm.home.data.MainRepository

interface MainUsecase {
    suspend fun getCharacter(): Result<CharacterHero>
}

class MainUseCaseImpl(private val repository: MainRepository) : MainUsecase {
    override suspend fun getCharacter(): Result<CharacterHero> {
        return repository.getCharacter()
    }
}