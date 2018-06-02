package com.rviannaoliveira.repository.marvel.remote.service

import com.rviannaoliveira.repository.marvel.remote.model.MarvelCharacterDataWrapper
import com.rviannaoliveira.repository.marvel.remote.model.MarvelComicDataWrapper
import com.rviannaoliveira.repository.marvel.remote.model.MarvelSeriesDataWrapper
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface MarvelService {

    @GET("v1/public/characters")
    fun getCharacters(@Query("limit") limit: Int, @Query("offset") offset: Int): Flowable<MarvelCharacterDataWrapper>

    @GET("v1/public/characters")
    fun getCharactersBeginLetter(@Query("limit") limit: Int, @Query("nameStartsWith") letter: String): Flowable<MarvelCharacterDataWrapper>

    @GET("v1/public/comics")
    fun getComics(@Query("limit") limit: Int, @Query("offset") offset: Int): Flowable<MarvelComicDataWrapper>

    @GET("v1/public/comics")
    fun getComicsBeginLetter(@Query("limit") limit: Int, @Query("titleStartsWith") titleStartsWith: String): Flowable<MarvelComicDataWrapper>

    @GET("/v1/public/characters/{characterId}")
    fun getCharacter(@Path("characterId") characterId: Int?): Flowable<MarvelCharacterDataWrapper>

    @GET("/v1/public/comics/{comicId}")
    fun getComic(@Path("comicId") characterId: Int?): Flowable<MarvelComicDataWrapper>

    @GET
    fun getGenericComic(@Url url: String): Flowable<MarvelComicDataWrapper>

    @GET
    fun getGenericSeries(@Url url: String): Flowable<MarvelSeriesDataWrapper>

    @GET
    fun getGenericCharacter(@Url url: String): Flowable<MarvelCharacterDataWrapper>

}





