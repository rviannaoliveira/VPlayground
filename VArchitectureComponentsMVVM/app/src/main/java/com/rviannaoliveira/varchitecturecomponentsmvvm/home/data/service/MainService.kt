package com.rviannaoliveira.varchitecturecomponentsmvvm.home.data.service

import br.com.uol.ps.pagvendas.coreshared.networking.NetworkResponse
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.data.model.CharacterResponse
import retrofit2.http.GET

interface MainService {
    @GET("character")
    suspend fun getCharacter(): NetworkResponse<CharacterResponse>
}