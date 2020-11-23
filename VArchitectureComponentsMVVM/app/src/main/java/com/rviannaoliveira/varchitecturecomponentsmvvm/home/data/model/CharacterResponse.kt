package com.rviannaoliveira.varchitecturecomponentsmvvm.home.data.model

import com.rviannaoliveira.varchitecturecomponentsmvvm.home.domain.CharacterHero

data class CharacterResponse(
    val id: Int,
    val name: String,
    val description: String,
    val hero: Boolean
)

fun CharacterResponse.toCharacter(): CharacterHero =
    CharacterHero(
        id = id,
        name = name,
        description = description,
        isHero = hero
    )
