package com.rviannaoliveira.varchitecturecomponentsmvvm.home.data

import br.com.uol.ps.pagvendas.coreshared.networking.toResult
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.data.model.toCharacter
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.data.service.MainService
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.domain.CharacterHero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

interface MainRepository {
    suspend fun getCharacter(): Result<CharacterHero>
}

class MainRepositoryImpl(private val service: MainService) : MainRepository {

    override suspend fun getCharacter(): Result<CharacterHero> =
        service.getCharacter().toResult().map {
            throw Exception()
            it.toCharacter()
        }
}

