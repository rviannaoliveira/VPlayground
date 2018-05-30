package com.rviannaoliveira.repository.marvel

import com.rviannaoliveira.repository.marvel.api.MarvelService
import com.rviannaoliveira.repository.model.*
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers


interface IApiData {
    fun getMarvelCharacters(offset: Int): Flowable<List<MarvelCharacter>>
    fun getMarvelComics(offset: Int): Flowable<List<MarvelComic>>
    fun getDetailCharacter(id: Int?): Flowable<MarvelCharacter>
    fun getDetailComic(id: Int?): Flowable<MarvelComic>
    fun getMarvelCharactersBeginLetter(letter: String): Flowable<List<MarvelCharacter>>
    fun getMarvelComicsBeginLetter(letter: String): Flowable<List<MarvelComic>>
}

class MarvelApiDataImpl(private var marvelService: MarvelService = MarvelClient().createService(MarvelService::class.java)) : IApiData {

    companion object {
        var comicsCache = ArrayList<MarvelComic>()
        var detailCharacterCache = HashMap<Int, MarvelCharacter>()
        var LIMIT_REGISTER = 30
        var detailComicCache = HashMap<Int, MarvelComic>()
    }


    override fun getMarvelCharacters(offset: Int): Flowable<List<MarvelCharacter>> {
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

    override fun getMarvelCharactersBeginLetter(letter: String): Flowable<List<MarvelCharacter>> {
        val response = marvelService.getCharactersBeginLetter(100, letter)
        return response.subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap({ dataWrappers ->
                    val results = dataWrappers.data?.results
                    Flowable.fromArray(results)
                })
    }

    override fun getMarvelComicsBeginLetter(letter: String): Flowable<List<MarvelComic>> {
        val response = marvelService.getComicsBeginLetter(100, letter)
        return response.subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap({ dataWrappers ->
                    val results = dataWrappers.data?.results
                    Flowable.fromArray(results)
                })
    }

    override fun getMarvelComics(offset: Int): Flowable<List<MarvelComic>> {
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

    override fun getDetailCharacter(id: Int?): Flowable<MarvelCharacter> {
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
                            Function3<MarvelCharacter?, MarvelComicDataWrapper, MarvelSeriesDataWrapper, MarvelCharacter>({ character, comics, series ->
                                character.comicList = comics.data?.results
                                character.seriesList = series.data?.results
                                character.id?.let { detailCharacterCache.put(it, character) }
                                character
                            }))
                })
    }

    override fun getDetailComic(id: Int?): Flowable<MarvelComic> {
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
                            BiFunction<MarvelComic?, MarvelCharacterDataWrapper, MarvelComic>({ comic, characters ->
                                comic.charactersList = characters.data?.results
                                comic.id?.let { detailComicCache.put(it, comic) }
                                comic
                            }))
                })
    }

    private fun getMarvelCharacterComics(data: MarvelCharacter?): Flowable<MarvelComicDataWrapper>? {
        return data?.comics?.collectionURI?.let {
            marvelService.getGenericComic(it)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    private fun getMarvelCharacterSeries(data: MarvelCharacter?): Flowable<MarvelSeriesDataWrapper>? {
        return data?.series?.collectionURI?.let {
            marvelService.getGenericSeries(it)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    private fun getMarvelComicCharacter(data: MarvelComic?): Flowable<MarvelCharacterDataWrapper>? {
        return data?.characters?.collectionURI?.let {
            marvelService.getGenericCharacter(it)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

}