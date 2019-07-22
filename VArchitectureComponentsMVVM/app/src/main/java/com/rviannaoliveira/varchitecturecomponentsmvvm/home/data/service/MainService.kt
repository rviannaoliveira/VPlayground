package com.rviannaoliveira.varchitecturecomponentsmvvm.home.data.service

import android.app.Application
import com.google.gson.Gson
import com.rviannaoliveira.varchitecturecomponentsmvvm.extensions.getJsonString
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.model.CharacterResponse
import io.reactivex.Single
import javax.inject.Inject

interface MainService {
    fun getCharacter(): Single<CharacterResponse>
}

class MainServiceImpl @Inject constructor(
    private val application: Application,
    private val gson: Gson
) : MainService {

    override fun getCharacter(): Single<CharacterResponse> {
        return Single.create { emitter ->
            try {
                emitter.onSuccess(
                    gson.fromJson(
                        application.getJsonString("character.json"),
                        CharacterResponse::class.java
                    )
                )
            } catch (ex: Exception) {
                emitter.onError(ex)
            }
        }
    }
}