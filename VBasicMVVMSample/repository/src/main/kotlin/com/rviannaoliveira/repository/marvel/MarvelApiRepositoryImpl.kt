package com.rviannaoliveira.repository.marvel

import com.rviannaoliveira.repository.marvel.presentation.model.MarvelCharacter
import com.rviannaoliveira.repository.marvel.remote.model.*
import com.rviannaoliveira.repository.marvel.remote.service.MarvelService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


interface IMarvelApiRepository {
    fun getMarvelCharacters(offset: Int): Single<List<MarvelCharacter>>
    fun getMarvelComics(offset: Int): Single<List<MarvelComicResponse>>
    fun getDetailCharacter(id: Int?): Single<MarvelCharacterResponse>
    fun getDetailComic(id: Int?): Single<MarvelComicResponse>
}

class MarvelApiRepositoryImpl @Inject constructor (private var marvelService: MarvelService) : IMarvelApiRepository {

    companion object {
        var comicsCache = ArrayList<MarvelComicResponse>()
        var detailCharacterCache = HashMap<Int, MarvelCharacterResponse>()
        var LIMIT_REGISTER = 30
        var detailComicCache = HashMap<Int, MarvelComicResponse>()
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

    override fun getMarvelComics(offset: Int): Single<List<MarvelComicResponse>> {
        if (offset == 0 && comicsCache.isNotEmpty()) {
            return Single.just(comicsCache)
        }

        val response = marvelService.getComics(LIMIT_REGISTER, offset)
        return response.subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap({ dataWrappers ->
                    val results = dataWrappers.data?.results
                    results?.let { comicsCache.addAll(it) }
                    Single.just(dataWrappers.data?.results)
                })
    }

    override fun getDetailCharacter(id: Int?): Single<MarvelCharacterResponse> {
        val response = marvelService.getCharacter(id)
        return response.subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap({ dataWrappers ->
                    Single.just(dataWrappers.data?.results?.get(0))
                })
                .flatMap({ data ->
                    Single.zip(Single.just(data),
                            getMarvelCharacterComics(data),
                            getMarvelCharacterSeries(data),
                            Function3<MarvelCharacterResponse?, MarvelComicDataWrapper, MarvelSeriesDataWrapper, MarvelCharacterResponse>({ character, comics, series ->
                                character.comicList = comics.data?.results
                                character.seriesList = series.data?.results
                                character.id?.let { detailCharacterCache.put(it, character) }
                                character
                            }))
                })
    }

    override fun getDetailComic(id: Int?): Single<MarvelComicResponse> {
        val response = marvelService.getComic(id)
        return response.subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap ({ dataWrappers ->
                    Single.just(dataWrappers.data?.results?.get(0))
                })
                .flatMap({ data ->
                    Single.zip(Single.just(data),
                            getMarvelComicCharacter(data),
                            BiFunction<MarvelComicResponse?, MarvelCharacterDataWrapper, MarvelComicResponse>({ comic, characters ->
                                comic.charactersList = characters.data?.results
                                comic.id?.let { detailComicCache.put(it, comic) }
                                comic
                            }))
                })
    }

    private fun getMarvelCharacterComics(data: MarvelCharacterResponse?): Single<MarvelComicDataWrapper>? {
        return data?.comics?.collectionURI?.let {
            marvelService.getGenericComic(it)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    private fun getMarvelCharacterSeries(data: MarvelCharacterResponse?): Single<MarvelSeriesDataWrapper>? {
        return data?.series?.collectionURI?.let {
            marvelService.getGenericSeries(it)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    private fun getMarvelComicCharacter(data: MarvelComicResponse?): Single<MarvelCharacterDataWrapper>? {
        return data?.characters?.collectionURI?.let {
            marvelService.getGenericCharacter(it)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

}