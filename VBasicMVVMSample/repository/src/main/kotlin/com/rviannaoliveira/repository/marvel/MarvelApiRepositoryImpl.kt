package com.rviannaoliveira.repository.marvel

import com.rviannaoliveira.repository.marvel.remote.model.*
import com.rviannaoliveira.repository.marvel.remote.service.MarvelService
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers


interface IMarvelApiRepository {
    fun getMarvelCharacters(offset: Int): Flowable<List<MarvelCharacterResponse>>
    fun getMarvelComics(offset: Int): Flowable<List<MarvelComicResponse>>
    fun getDetailCharacter(id: Int?): Flowable<MarvelCharacterResponse>
    fun getDetailComic(id: Int?): Flowable<MarvelComicResponse>
    fun getMarvelCharactersBeginLetter(letter: String): Flowable<List<MarvelCharacterResponse>>
    fun getMarvelComicsBeginLetter(letter: String): Flowable<List<MarvelComicResponse>>
}

class MarvelApiRepositoryImpl constructor (private var marvelService: MarvelService) : IMarvelApiRepository {

    companion object {
        var comicsCache = ArrayList<MarvelComicResponse>()
        var detailCharacterCache = HashMap<Int, MarvelCharacterResponse>()
        var LIMIT_REGISTER = 30
        var detailComicCache = HashMap<Int, MarvelComicResponse>()
    }


    override fun getMarvelCharacters(offset: Int): Flowable<List<MarvelCharacterResponse>> {
        val response = marvelService.getCharacters(LIMIT_REGISTER, offset)
        return response
                .subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap({ dataWrappers ->
                    val results = dataWrappers.data?.results
                    Flowable.fromArray(results)
                })
    }

    override fun getMarvelCharactersBeginLetter(letter: String): Flowable<List<MarvelCharacterResponse>> {
        val response = marvelService.getCharactersBeginLetter(100, letter)
        return response.subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap({ dataWrappers ->
                    val results = dataWrappers.data?.results
                    Flowable.fromArray(results)
                })
    }

    override fun getMarvelComicsBeginLetter(letter: String): Flowable<List<MarvelComicResponse>> {
        val response = marvelService.getComicsBeginLetter(100, letter)
        return response.subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap({ dataWrappers ->
                    val results = dataWrappers.data?.results
                    Flowable.fromArray(results)
                })
    }

    override fun getMarvelComics(offset: Int): Flowable<List<MarvelComicResponse>> {
        if (offset == 0 && comicsCache.isNotEmpty()) {
            return Flowable.fromArray(comicsCache)
        }

        val response = marvelService.getComics(LIMIT_REGISTER, offset)
        return response.subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap({ dataWrappers ->
                    val results = dataWrappers.data?.results
                    results?.let { comicsCache.addAll(it) }
                    Flowable.fromArray(dataWrappers.data?.results)
                })
    }

    override fun getDetailCharacter(id: Int?): Flowable<MarvelCharacterResponse> {
        val response = marvelService.getCharacter(id)
        return response.subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap({ dataWrappers ->
                    Flowable.just(dataWrappers.data?.results?.get(0))
                })
                .flatMap({ data ->
                    Flowable.zip(Flowable.just(data),
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

    override fun getDetailComic(id: Int?): Flowable<MarvelComicResponse> {
        val response = marvelService.getComic(id)
        return response.subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap({ dataWrappers ->
                    Flowable.just(dataWrappers.data?.results?.get(0))
                })
                .flatMap({ data ->
                    Flowable.zip(Flowable.just(data),
                            getMarvelComicCharacter(data),
                            BiFunction<MarvelComicResponse?, MarvelCharacterDataWrapper, MarvelComicResponse>({ comic, characters ->
                                comic.charactersList = characters.data?.results
                                comic.id?.let { detailComicCache.put(it, comic) }
                                comic
                            }))
                })
    }

    private fun getMarvelCharacterComics(data: MarvelCharacterResponse?): Flowable<MarvelComicDataWrapper>? {
        return data?.comics?.collectionURI?.let {
            marvelService.getGenericComic(it)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    private fun getMarvelCharacterSeries(data: MarvelCharacterResponse?): Flowable<MarvelSeriesDataWrapper>? {
        return data?.series?.collectionURI?.let {
            marvelService.getGenericSeries(it)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    private fun getMarvelComicCharacter(data: MarvelComicResponse?): Flowable<MarvelCharacterDataWrapper>? {
        return data?.characters?.collectionURI?.let {
            marvelService.getGenericCharacter(it)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

}