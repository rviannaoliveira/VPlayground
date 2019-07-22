package com.rviannaoliveira.varchitecturecomponentsmvvm.home.data

import android.app.Application
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.data.service.MainService
import com.rviannaoliveira.varchitecturecomponentsmvvm.home.model.Character
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface MainRepository {
    fun getCharacter(): Single<Character>
}

class MainRepositoryImpl @Inject constructor(private val service: MainService) : MainRepository {
 @Inject
 lateinit var application: Application
    override fun getCharacter(): Single<Character> =
        service.getCharacter().map {
            it.toCharacter()
        }.delay(3,TimeUnit.SECONDS)
}
