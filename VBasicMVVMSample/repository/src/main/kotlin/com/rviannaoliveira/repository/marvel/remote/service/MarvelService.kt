package com.rviannaoliveira.repository.marvel.remote.service

import com.rviannaoliveira.repository.marvel.remote.model.MarvelCharacterDataWrapper
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelService {

    @GET("v1/public/characters")
    fun getCharacters(@Query("limit") limit: Int, @Query("offset") offset: Int): Single<MarvelCharacterDataWrapper>
}





