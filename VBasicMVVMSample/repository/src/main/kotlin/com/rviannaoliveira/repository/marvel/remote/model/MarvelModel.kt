package com.rviannaoliveira.repository.marvel.remote.model

import com.google.gson.annotations.SerializedName
import com.rviannaoliveira.repository.marvel.presentation.model.MarvelCharacter

open class BaseModelMarvel {
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("name")
    val name: String = ""
    @SerializedName("description")
    var description: String? = null
    @SerializedName("thumbnail")
    var thumbMail: MarvelImage? = null
    @SerializedName("title")
    var title: String? = null
}

open class BaseModelMarvelContainer {
    @SerializedName("limit")
    val limit: Int? = null
    @SerializedName("count")
    val count: Int? = null
    @SerializedName("total")
    val total: Int? = null
}

open class BaseModelMarvelList {
    @SerializedName("available")
    val available: Int? = null
    @SerializedName("returned")
    val returned: Int? = null
    @SerializedName("collectionURI")
    val collectionURI: String? = null
}

open class BaseModelMarvelSummary {
    @SerializedName("resourceURI")
    val resourceURI: String? = null
    @SerializedName("name")
    val name: String? = null
}

open class BaseModelMarvelWrapper {
    @SerializedName("code")
    val code: Int? = null
    @SerializedName("status")
    val status: String? = null
}

data class MarvelCharacterDataContainer(
        @SerializedName("results") var results: ArrayList<MarvelCharacterResponse>? = null) : BaseModelMarvelContainer()

data class MarvelCharacterDataWrapper(@SerializedName("data")
                                      var data: MarvelCharacterDataContainer? = null) : BaseModelMarvelWrapper()

data class MarvelCharacterList(@SerializedName("items")
                               val items: ArrayList<MarvelCharacterSummary>? = null) : BaseModelMarvelList()


data class MarvelComicDataWrapper(@SerializedName("data")
                                  val data: MarvelComicDataContainer? = null) : BaseModelMarvelWrapper()

data class MarvelComicList(@SerializedName("items")
                           val items: ArrayList<MarvelComicSummary>? = null) : BaseModelMarvelList()

data class MarvelComicDataContainer(@SerializedName("results")
                                    val results: ArrayList<MarvelComicResponse>? = null) : BaseModelMarvelContainer()

data class MarvelComicPrice(@SerializedName("type")
                            val type: String? = null,
                            @SerializedName("price")
                            val price: Float? = null)

data class MarvelImage(
        @SerializedName("path")
        private val path: String? = null,
        @SerializedName("extension")
        private val extension: String? = null) {

    fun getPathExtension(): String {
        return "$path.$extension"
    }
}

data class MarvelSeriesResponse(@SerializedName("endYear")
                                val endYear: Int? = null,
                                @SerializedName("startYear")
                                val startYear: Int? = null) : BaseModelMarvel()

data class MarvelSeriesContainer(@SerializedName("results")
                                 val results: ArrayList<MarvelSeriesResponse>? = null) : BaseModelMarvelContainer()

data class MarvelSeriesDataWrapper(@SerializedName("data")
                                   val data: MarvelSeriesContainer? = null) : BaseModelMarvelWrapper()


data class MarvelSeriesList(@SerializedName("items")
                            val items: ArrayList<MarvelSeriesSummary>? = null) : BaseModelMarvelList()

data class MarvelStory(@SerializedName("type")
                       val type: String? = null,
                       @SerializedName("resourceURI")
                       val resourceURI: String? = null) : BaseModelMarvel()

data class MarvelStoryDataContainer(val results: ArrayList<MarvelStory>? = null) : BaseModelMarvelContainer()

data class MarvelStoryDataWrapper(@SerializedName("data")
                                  val data: MarvelStoryDataContainer? = null) : BaseModelMarvelWrapper()

data class MarvelStoryList(@SerializedName("items")
                           val items: ArrayList<MarvelStorySummary>? = null) : BaseModelMarvelList()

data class MarvelStorySummary(@SerializedName("type")
                              val type: String? = null)

data class MarvelTextObjects(@SerializedName("type")
                             val type: String? = null,
                             @SerializedName("name")
                             val text: String? = null,
                             @SerializedName("language")
                             val language: String? = null)

data class MarvelUrl(@SerializedName("type")
                     val type: String? = null,
                     @SerializedName("url")
                     val url: String? = null)

class MarvelComicSummary : BaseModelMarvelSummary()

class MarvelCharacterSummary : BaseModelMarvelSummary()

class MarvelSeriesSummary : BaseModelMarvelSummary()

data class MarvelComicResponse  constructor(
        @SerializedName("pageCount")
        val pageCount: Int? = null,
        @SerializedName("urls")
        val urls: ArrayList<MarvelUrl>? = null,
        @SerializedName("collections")
        val collections: ArrayList<MarvelComicSummary>? = null,
        @SerializedName("images")
        val images: ArrayList<MarvelImage>? = null,
        @SerializedName("characters")
        val characters: MarvelCharacterList? = null,
        @SerializedName("stories")
        val stories: MarvelStoryList? = null,
        @SerializedName("prices")
        val prices: ArrayList<MarvelComicPrice>? = null,
        @SerializedName("textObjects")
        val textObjects: ArrayList<MarvelTextObjects>? = null,
        var charactersList: ArrayList<MarvelCharacterResponse>? = null,
        var storiesList: ArrayList<MarvelStory>? = null
) : BaseModelMarvel()

data class MarvelCharacterResponse(@SerializedName("urls")
                                   val urls: ArrayList<MarvelUrl>? = null,
                                   @SerializedName("comics")
                                   var comics: MarvelComicList? = null,
                                   @SerializedName("stories")
                                   val stories: MarvelStoryList? = null,
                                   @SerializedName("series")
                                   val series: MarvelSeriesList? = null,
                                   var comicList: ArrayList<MarvelComicResponse>? = null,
                                   var seriesList: ArrayList<MarvelSeriesResponse>? = null
) : BaseModelMarvel()


fun MarvelCharacterResponse.toMarvelCharacter() =
        MarvelCharacter(
                name = name,
                image = thumbMail?.getPathExtension())