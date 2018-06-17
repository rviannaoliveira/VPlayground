package com.rviannaoliveira.repository.marvel

import com.rviannaoliveira.repository.marvel.presentation.model.MarvelCharacter
import com.rviannaoliveira.repository.marvel.remote.model.toMarvelCharacter
import com.rviannaoliveira.repository.marvel.remote.service.MarvelService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


interface IMarvelApiRepository {
    fun getMarvelCharacters(offset: Int): Single<List<MarvelCharacter>>
}

class MarvelApiRepositoryImpl @Inject constructor (private var marvelService: MarvelService) : IMarvelApiRepository {

    companion object {
        const val LIMIT_REGISTER = 30
    }

    override fun getMarvelCharacters(offset: Int): Single<List<MarvelCharacter>> {
        val response = marvelService.getCharacters(LIMIT_REGISTER, offset)
        return response
                .subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap({ dataWrappers ->
                    Single.just(dataWrappers.data.results.map { it.toMarvelCharacter() })
                })
    }
}